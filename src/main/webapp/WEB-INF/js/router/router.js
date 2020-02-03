import Vue from 'vue'
import VueRouter from 'vue-router'
import Info from 'components/Info.vue'
import Periods from 'components/periods/Periods.vue'
import AddPeriod from 'components/periods/AddPeriod.vue'
import AddUser from 'components/users/AddUser.vue'
import GetUserWrap from 'components/wraps/GetUserWrap.vue'
import GetUsersWrap from 'components/wraps/GetUsersWrap.vue'

Vue.use(VueRouter)

var baseUrl = '/days-off-system'

const routes = [
	{ name: 'info', path: baseUrl + '/info', component: Info },
	{ name: 'periods', path: baseUrl + '/periods', component: Periods },
	{ name: 'addPeriod', path: baseUrl + '/addPeriod', component: AddPeriod },
	{ name: 'addUser', path: baseUrl + '/addUser', component: AddUser },
	{ name: 'getUser', path: baseUrl + '/getUser', component: GetUserWrap },
	{ name: 'getUsers', path: baseUrl + '/getUsers', component: GetUsersWrap },

]

export default new VueRouter({
	routes,
	mode: 'history',
	 
})