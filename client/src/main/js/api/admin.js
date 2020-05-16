import Vue from 'vue'

export default {
	getUserByEmail: email => Vue.resource('/days-off-system/api/v1/admin/getUserByEmail').get({email: email}),
	addUser: user => Vue.resource('/days-off-system/api/v1/admin/addUser').save(user),
	updateUser: user => Vue.resource('/days-off-system/api/v1/admin/updateUser').update(user),
	getUsers: () => Vue.resource('/days-off-system/api/v1/admin/getAll').get(),
	getPeriodsByUserId: id => Vue.resource('/days-off-system/api/v1/admin/getPeriodsByUserId').get({id: id}),
    addPeriod: period => Vue.resource('/days-off-system/api/v1/admin/addPeriod').save(period),
    updatePeriod: period => Vue.resource('/days-off-system/api/v1/admin/updatePeriod').update(period),
    removePeriod: id => Vue.resource('/days-off-system/api/v1/admin/deletePeriodById').remove({id: id})
}