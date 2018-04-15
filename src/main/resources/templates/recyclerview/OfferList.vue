<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<div id="offer-list">
    <RecyclerView
            :prerender="10"
            style="height: calc(100vh - 50px)"
            :fetch="MiFetch"
            :item="MiItem"
            :tombstone="MiTomstone"
    ></RecyclerView>
</div>
</body>

<script>
    import MiItem from './OfferItem.vue'
    import MiTomstone from './OfferTombstone.vue'
    import MiFetch from './mi-fetch'

    export default {
        name: 'app',
        data () {
            return {
                MiFetch,
                MiItem,
                MiTomstone
            }
        }
    }
</script>
</html>