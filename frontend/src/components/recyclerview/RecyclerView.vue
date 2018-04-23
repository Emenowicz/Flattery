<style scoped>
  /*html,*/
  /*body {*/
  /*padding: 0;*/
  /*margin: 0;*/
  /*height: 100%;*/
  /*width: 100%;*/
  /*background: #000;*/
  /*}*/

  .mi-item {
    padding: 0 0 3px;
    position: absolute;
    background: #fff;
    list-style: none;
    width: 100%;
  }

  .mi-item::after {
    content: "";
    position: absolute;
    bottom: 1.5px;
    left: 150px;
    right: 0;
    border-bottom: 1px solid #e4e4e4;
  }

  .version-item {
    display: flex;
    display: -webkit-box;
    box-align: center;
    -webkit-box-align: center;
    width: 100%;
  }

  .version-item-img {
    width: 180px;
    height: 180px;
    position: relative;
  }

  .version-item-img img {
    width: 100%;
  }

  .version-item .version-item-intro {
    -webkit-box-flex: 1;
    box-flex: 1;
    display: block;
    padding: 0 15px 5px;
  }

  .version-item .version-item-intro .version-item-name {
    font-size: 14px;
    color: rgba(0, 0, 0, .87);
    margin-bottom: 8px;
  }

  .version-item .version-item-intro .version-item-brief {
    font-size: 12px;
    color: rgba(0, 0, 0, .54);
    margin-bottom: 8px;
    line-height: 15px;
    overflow: hidden;
  }

  .version-item-brief p {
    text-overflow: ellipsis;
    overflow: hidden;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    word-break: break-all;
  }

  .version-item .version-item-intro .version-item-intro-price {
    font-size: 15px;
    position: relative;
    margin-left: 10px;
  }

  .version-item .version-item-intro span {
    color: #ff6000;
  }

  .mi-item.tombstone p {
    width: 100%;
    height: 0.5em;
    background-color: #ccc;
    margin: 0.5em 0;
  }
</style>
<script>
import offerItem from './OfferItem.vue'
import offerTombstone from './OfferTombstone.vue'
import axios from 'axios'
export default ({
  data () {
    return {
      item: offerItem,
      tombstone: offerTombstone,
      fetch: fetchOld,
      offerItems: [],
      loading: false,
      post: false,
      error: null
    }
  },
  props: {
    search: ''
  },
  methods: {
    async searchOffers () {
      try {
        this.loading = true
        await axios.post(`/offers`, {
          city: this.search,
          offerType: 'Room',
          roomType: 'SinglePerson',
          minPrice: '700',
          maxPrice: '2000'
        }).then(result => {
          console.log(result.data)
          this.loading = false
          this.post = true
          this.offerItems = result.data
          baseData = result.data
        })
      } catch (e) {
        console.log(e.message)
      }
    },
    fetchFun (limit, skip) {
      return Promise.resolve({
        list: this.offerItems,
        count: this.offerItems.getLength

      })
    }
  },
  created () {
    this.searchOffers()
  }
})

let baseData = [
  {
    'id': '0',
    'title': 'Spoko pokoj w spoko mieszkaniu. Ogolnie fajno!',
    'city': 'Wroclaw',
    'price': '1000.00',
    'photoUrl': 'https://www.rmf.fm/_files/Short_foto/625/bd2107dd15db877c81d5aa527b6c0cbc.jpga'
  },
  {
    'id': '1',
    'title': 'Rewalacja, mowie ci.',
    'city': 'Zalesie',
    'price': '420.00',
    'photoUrl': 'https://www.pieknowdomu.pl/media/catalog/product/cache/1/image/1000x1000/17f82f742ffe127f42dca9de82fb58b1/0/6/060700779.jpg'
  }
]

let id = 0

function pickOne () {
  baseData = this.offerItems
  return baseData[Math.floor(Math.random() * baseData.length)]
}

function getItem () {
  return new Promise(resolve => {
    var item = pickOne()
    item.id = ++id
    var image = new Image()
    image.src = item.photoUrl
    image.addEventListener('load', () => {
      resolve(item)
    })
    image.addEventListener('error', () => {
      item.photoUrl = ''
      resolve(item)
    })
  })
}

function query (limit, skip) {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      var items = []
      for (var i = 0; i < baseData.length; i++) {
        items[i] = getItem()
      }
      resolve(Promise.all(items))
    }, 200)
  })
}

function fetchOld (limit, skip) {
  limit = Math.max(30, limit)
  return query(limit, skip)
    .then(list => {
      return {
        list: list,
        count: list.length
      }
    })
}
</script>
<template>
  <div id="recyclerview">
    <div class="loading" v-if="loading">
      Loading...
    </div>
    <RecyclerView v-if="post" class="content"
                  style="height: 80vh"
                  :prerender="30"
                  :fetch="fetch"
                  :item="item"
                  :tombstone="tombstone"
    ></RecyclerView>
  </div>
</template>
