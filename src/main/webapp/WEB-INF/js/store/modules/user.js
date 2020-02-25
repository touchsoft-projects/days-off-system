import userApi from 'api/user'

const state = {
    periods: [],
}

const getters = {
    info(state) {
        return state.info
    },
    getPeriods(state) {
        return state.periods
    }
}

const mutations = {
    setPeriods(state, periods) {
        state.periods = periods
    },
    addPeriod(state, period) {
        state.periods = [
            ...state.periods,
            period
        ]
    },
    updatePeriod(state, period) {
        const updateIndex = state.periods.findIndex(item => item.id === period.id)
        var updatedPeriod = Object.assign({}, period)
        if (updateIndex > -1) {
            state.periods = [
            ...state.periods.slice(0, updateIndex),
            updatedPeriod,
            ...state.periods.slice(updateIndex + 1)
            ]
        }
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
    async getPeriodsAction({commit}) {
        const result = await userApi.getPeriods()
        const data = await result.json()
        commit('setPeriods', data)
    },
    async addPeriodAction({commit}, payload) {
        const result = await userApi.addPeriod(payload.period)
        if (result.ok) {
            commit('addPeriod', payload.period)
        }
        payload.checkResult(result, payload.successMessage, payload.failureMessage)
    },
    async updatePeriodAction({commit}, payload) {
        const result = await userApi.updatePeriod(payload.period)
         if (result.ok) {
            commit('updatePeriod', payload.period)
        }
        payload.checkResult(result, payload.successMessage, payload.failureMessage)
    },
    async removePeriodAction({commit}, payload) {
        const result = await userApi.removePeriod(payload.id)
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

function getIndex(period) {
    return state.periods.findIndex(item => item.id === period.id)
}