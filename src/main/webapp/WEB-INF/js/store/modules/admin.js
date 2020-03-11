import adminApi from 'api/admin'

const state = {
    users: [],
    periods: [],
    user: {},
}

const getters = {
    getUsers(state) {
        return state.users
    },
    getPeriods(state) {
        return state.periods
    },
    getUser(state) {
        return state.user
    },
}

const mutations = {
    setUsers(state, users) {
        state.users = users
    },
    setUser(state, user) {
        state.user = user
    },
    addPeriod(state, period) {
        state.periods = [
            ...state.periods,
            period
        ]
    },
    setPeriods(state, periods) {
        state.periods = periods
    },
    updatePeriod(state, period) {
        const updateIndex = state.periods.findIndex(item => item.id === period.id)
        var updatedPeriod = Object.assign({}, period)
        state.periods = [
            ...state.periods.slice(0, updateIndex),
            updatedPeriod,
            ...state.periods.slice(updateIndex + 1)
        ]
    },
    removePeriod(state, id) {
        const deletionIndex = state.periods.findIndex(item => item.id === id)

        if (deletionIndex > -1) {
            state.periods = [
                ...state.periods.slice(0, deletionIndex),
                ...state.periods.slice(deletionIndex + 1)
            ]
        }
    },
}

const actions = {
    async getUsersAction({commit}) {
        const result = await adminApi.getUsers()
        const data = await result.json()
        commit('setUsers', data)
    },
    async getPeriodsAction({commit}, payload) {
        const result = await adminApi.getPeriodsByUserId(payload.userId)
        const data = await result.json()
        commit('setPeriods', data)
    },
    async addPeriodAction({commit}, payload) {
        const result = await adminApi.addPeriod(payload.period)
        if (result.ok) {
            const id = await result.json()
            payload.period.id = id
            commit('addPeriod', payload.period)
        }
        payload.checkResult(result, payload.successMessage, payload.failureMessage)
    },
    async updatePeriodAction({commit}, payload) {
        const result = await adminApi.updatePeriod(payload.period)
         if (result.ok) {
            commit('updatePeriod', payload.period)
        }
        payload.checkResult(result, payload.successMessage, payload.failureMessage)
    },
    async removePeriodAction({commit}, payload) {
        const result = await adminApi.removePeriod(payload.id)
        if (result.ok) {
            commit('removePeriod', payload.id)
        }
    },

}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}