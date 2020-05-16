import Vue from 'vue'
import Vuex from 'vuex'

import userStore from './modules/user'
import adminStore from './modules/admin'
import userApi from 'api/user'
import {ADMIN, EMPLOYEE} from 'constants/profile.js'

Vue.use(Vuex)

const store = new Vuex.Store({
	state: {
		user: {},
		profile: {},
	},
  	getters: {
  		getUser(state) {
	        return state.user
	    },
	    getProfile(state) {
	        return state.profile
	    },
  	},
  	mutations: {
  		setUser(state, user) {
	        state.user = user
	    },
	    setProfile(state, profile) {
	        state.profile = profile
	    },
  	},
  	actions: {
  		async getUserAction({commit, state}) {
	        const result = await userApi.getUser()
	        const data = await result.json()
	        commit('setUser', data[0])
	    }
  	},
	modules: {
        userStore,
        adminStore
    }
})


store.dispatch('getUserAction').then(() => {
	var profile
    if (hasRoleEmployee(store.getters['getUser'].roles)) {
    	profile = EMPLOYEE
    } else {
    	profile = ADMIN
    }
    store.commit('setProfile', profile)
})

function hasRoleEmployee(roles) {
    for (let role of roles) {
        if (role == "ROLE_EMPLOYEE") {
            return true
        }
    }
    return false
}  

export default store