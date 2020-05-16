import Vue from 'vue'

export default {
    getUser: () => Vue.resource('/days-off-system/api/v1/user/getUser').get(),
    getPeriods: () => Vue.resource('/days-off-system/api/v1/user/getPeriods').get(),
    addPeriod: period => Vue.resource('/days-off-system/api/v1/user/addPeriod').save(period),
    updatePeriod: period => Vue.resource('/days-off-system/api/v1/user/updatePeriod').update(period),
    removePeriod: id => Vue.resource('/days-off-system/api/v1/user/deletePeriodById').remove({id: id})
}