const baseData = [
    {
        "id": "4802",
        "location": "Wroclaw",
        "name": "LosowaNazwa",
        "price": "1499.00",
        "img_url": "//cdn.cnbj0.fds.api.mi-img.com/b2c-mimall-media/f11b3f5b4a1df7e9bdfce788b6506ebb.jpg"
    }]

let id = 0

function pickOne () {
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