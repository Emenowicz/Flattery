// new Vue({
//     el: "#app",
//     // render: h => h(App),
//     data: {
//         searchQuery: '',
//         offers: [],
//         offersLoaded: false,
//         message: "Welcome to Flattery",
//         appName: 'Flattery',
//         currentView: 'search'
//     },
//     methods: {
//         searchOffers: async function () {
//             try {
//                 await axios.post(`/offers`, {
//                     'city': this.searchQuery,
//                     query: 'balkon',
//                     minPrice: '500',
//                     maxPrice: '1200',
//                     roomType: 'SinglePerson',
//                     offerType: 'Room'
//
//                 }).then(result => {
//                     offers = result.data;
//                     console.log(offers);
//                 })
//             } catch (e) {
//                 console.log(e.message);
//             }
//         },
//     }
// });
//
