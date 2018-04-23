let baseData = [
  {
    'name': 'Spoko pokoj w spoko mieszkaniu. Ogolnie fajno!',
    'localization': 'Wroclaw',
    'price': '1000.00',
    'img_url': 'https://www.rmf.fm/_files/Short_foto/625/bd2107dd15db877c81d5aa527b6c0cbc.jpga'
  },
  {
    'name': 'Rewalacja, mowie ci.',
    'localization': 'Zalesie',
    'price': '420.00',
    'img_url': 'https://www.pieknowdomu.pl/media/catalog/product/cache/1/image/1000x1000/17f82f742ffe127f42dca9de82fb58b1/0/6/060700779.jpg'
  }
]

let id = 0

function pickOne () {
  baseData = this.items
  return baseData[Math.floor(Math.random() * baseData.length)]
}

function getItem () {
  return new Promise(resolve => {
    var item = pickOne()
    item.id = ++id
    var image = new Image()
    image.src = item.img_url
    image.addEventListener('load', () => {
      resolve(item)
    })
    image.addEventListener('error', () => {
      item.img_url = ''
      resolve(item)
    })
  })
}

function query (limit, skip) {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      var items = []
      for (var i = 0; i < limit; i++) {
        items[i] = getItem()
      }
      resolve(Promise.all(items))
    }, 200)
  })
}

export default function fetch (limit, skip) {
  limit = Math.max(30, limit)
  return query(limit, skip)
    .then(list => {
      return {
        list: list,
        count: 1000
      }
    })
}
