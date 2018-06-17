package zpi.flattery.webscrapper.strategy;

import io.reactivex.Single;
import okhttp3.HttpUrl;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import zpi.flattery.models.Offer;
import zpi.flattery.models.enums.OfferType;
import zpi.flattery.models.enums.RoomType;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.time.format.DateTimeFormatter.ofPattern;

public class GumtreeStrategy extends ScrapStrategy {

    private static final Logger LOGGER = Logger.getLogger(OlxStrategy.class.getName());
    private static final String BASE_URL = "www.gumtree.pl";

    private String urlToScrap;
    private RoomType roomType;
    private OfferType offerType;
    private double minPrice;
    private double maxPrice;
    private String query;
    private String location;

    private int daysOld;

    @Override
    public Single<List<Offer>> scrap() {
        ArrayList<Offer> scrappedOffers = new ArrayList<>();
        int currentPage = 1;
        //Scrapping offers
        try {
            Connection.Response response = Jsoup.connect(urlToScrap).followRedirects(true).execute();
            while (getPageNumberFromUrl(response.url().toString()) == currentPage && currentPage < 5) {
                Document doc = Jsoup.connect(urlToScrap).followRedirects(true).get();
                Elements offers = doc.select("li.result.pictures");
                offers.forEach(element -> {
                    Offer offerToAdd = getOfferFromElement(element);
                    if (offerToAdd != null && isOfferFreshEnough(offerToAdd, daysOld)) {
                        if (!scrappedOffers.contains(offerToAdd))
                            scrappedOffers.add(offerToAdd);
                    }
                });
                setUrlWithPage(++currentPage);
                response = Jsoup.connect(urlToScrap).followRedirects(true).execute();
            }
        } catch (IOException e) {
            System.err.println("For '" + urlToScrap + "': " + e.getMessage());
        }
        return addLatLang(scrappedOffers);
    }

    private Offer getOfferFromElement(Element offerElement) {
        String title;
        String link;
        double price;
        String publishingDate;
        String photoUrl;
        String location;

        Elements linkElement = offerElement.select("a[class^=href-link]");
        link = "https://" + BASE_URL + linkElement.attr("href");
        title = linkElement.text();
        if (link.equals("")) {
            LOGGER.log(Level.FINER, "Error: Offer contains null url, REJECTED");
            return null;
        }

        Elements imgElement = offerElement.select("img");
        photoUrl = imgElement.attr("src");
        if (photoUrl.equals("https://securet9.classistatic.com/1.1.736/images//loading.gif")) {
            photoUrl = "http://wpolskejedziemy.pl/web/uploaded_images/gallery/300x300/121366632662.png";
        }

        Elements locationElement = offerElement.select("div.category-location");
        locationElement = locationElement.select("span");
        int comaIndex = locationElement.text().indexOf(",");
        location = locationElement.text().substring(comaIndex + 2);

        Elements priceElement = offerElement.select("span.amount");
        String priceString = priceElement.text();
        if (!priceString.equals("")) {
            priceString = priceString.substring(0, priceString.length() - 2);
            priceString = priceString.replaceAll("\\s+", "");
            priceString = priceString.replaceAll(",", ".");
            price = Double.parseDouble(priceString);
        } else {
            price = 0.0;
        }
        Elements dateElement = offerElement.select("div.creation-date");
        dateElement = dateElement.select("span");
        String rawDate = dateElement.last().text();
        publishingDate = getDateFromString(rawDate);

        return new Offer(title, location, link, offerType, roomType, price, publishingDate, photoUrl);
    }

    private String getDateFromString(String dateString) {
        LocalDateTime currentDate = LocalDateTime.now();

        if (dateString.contains("godz")) {
            String[] dateElements = dateString.split("\\s");
            currentDate = currentDate.minusHours(Integer.parseInt(dateElements[0]));
            return currentDate.format(ofPattern("yyyy-MM-dd hh:mm"));
        } else if (dateString.contains("min")) {
            String[] dateElements = dateString.split("\\s");
            currentDate = currentDate.minusMinutes(Integer.parseInt(dateElements[0]));
            return currentDate.format(ofPattern("yyyy-MM-dd hh:mm"));
        } else {
            String[] dayAndMonth = dateString.split("-");
            LocalDate dateWithoutYearCheck = LocalDate.of(currentDate.getYear(), Integer.parseInt(dayAndMonth[1]), Integer.parseInt(dayAndMonth[0]));
            if (dateWithoutYearCheck.isAfter(LocalDate.now())) {
                return LocalDateTime.of(dateWithoutYearCheck.minusYears(1), LocalTime.of(12, 0)).format(ofPattern("yyyy-MM-dd hh:mm"));
            } else {
                return LocalDateTime.of(dateWithoutYearCheck, LocalTime.of(12, 0)).format(ofPattern("yyyy-MM-dd hh:mm"));
            }
        }
    }

    private int getPageNumberFromUrl(String url) {
        if (!url.contains("page")) {
            return 1;
        } else {
            int pageIndex = url.indexOf("page");
            return Integer.parseInt(url.substring(pageIndex + 5, pageIndex + 6));
        }
    }

    private void setUrlWithPage(int page) {
        HttpUrl.Builder httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host(BASE_URL);

        if (offerType != null) {
            switch (offerType) {
                case Flat:
                    httpUrl.addEncodedPathSegment("s-mieszkania-i-domy-do-wynajecia");
                    break;
                case Room:
                    httpUrl.addEncodedPathSegment("s-pokoje-do-wynajecia");
                    break;
                case House:
                    httpUrl.addEncodedPathSegment("s-mieszkania-i-domy-do-wynajecia");
                    break;
            }
        }
        String querySegment = "";
        if (location != null && query != null) {
            querySegment = location + " " + query;
        } else if (location != null) {
            querySegment = location;
        } else if (query != null) {
            querySegment = query;
        }
        querySegment = querySegment.replaceAll("\\s+", "+");
        httpUrl.addEncodedPathSegment(getNormalizedString(querySegment));

        if (page > 1) {
            httpUrl.addEncodedPathSegment("page-" + page);
        }

        urlToScrap = httpUrl.build().toString() + "/";

        if (offerType != null) {
            switch (offerType) {
                case Flat:
                    urlToScrap += "v1c9008q0";
                    break;
                case Room:
                    urlToScrap += "v1c9000q0";
                    break;
                case House:
                    urlToScrap += "v1c9008q0";
                    break;
            }
        }

        urlToScrap += "p" + page;

        if (minPrice != 0 && maxPrice != 0) {
            urlToScrap += "?pr=" + (int) minPrice + "," + (int) maxPrice;
        } else if (minPrice != 0) {
            urlToScrap += "?pr=" + (int) minPrice + ",";
        } else if (maxPrice != 0) {
            urlToScrap += "?pr=" + "," + (int) maxPrice;
        }
        System.out.println(urlToScrap);
    }

    @Override
    public void setScrapParameters(RoomType roomType, OfferType offerType, String query, double minPrice, double maxPrice, String location, Integer radius, Integer daysOld) {
        this.offerType = offerType;
        this.roomType = roomType;
        this.query = query;
        this.location = location;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        if (daysOld == null) {
            this.daysOld = 0;
        } else {
            this.daysOld = daysOld;
        }
        setUrlWithPage(1);
    }
}
