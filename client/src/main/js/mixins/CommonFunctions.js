import {ADMIN} from 'constants/profile.js'
export const commonFunctions = {
	data() {
		return {
			message: "",
			isShowMessage: false,
		}
	},
	methods: {
		checkResult(result, success, failure) {
			if (result.ok) {
				this.showMessage(success)
			} else {
				this.showMessage(failure)
			}
		},
		showMessage(message) {
			this.message = message
			this.isShowMessage = true
		},
		hideMessage() {
			this.message = ""
			this.isShowMessage = false
		},
		getIndex(list, id) {
			for (var i = 0; i < list.length; i++ ) {
				if (list[i].id === id) {
					return i
				}
			}
			return -1
		},
		isAdmin() {
			if (this.$store.getters['getProfile'] == ADMIN) {
				return true
			} else {
				return false
			}
		},
	}
}