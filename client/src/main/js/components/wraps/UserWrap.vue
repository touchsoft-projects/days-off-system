<template>
	<div>
		<label>Search by email: </label>
		<input type="text" v-model="email"/>
		<input type="button" value="Search" @click="searchUser" />
		<users v-show="isSearched" :users="users" :isUpdated="isUpdateUser"/>
	</div>
</template>

<script type="text/javascript">
	import Users from 'components/users/Users.vue'
	import adminApi from 'api/admin'
	import { EventBus } from 'EventBus.js'
	export default {
		components: {
			Users,
		},
		data() {
			return {
				users: [],
				isSearched: false,
				email: "",
				isUpdateUser: false,
			}
		},
		mounted() {
			EventBus.$on('update-data', this.updateUser)
		},
		beforeDestroy() {
			EventBus.$off('update-data', this.updateUser)
		},
		methods: {
		    searchUser() {
		        this.users.length = 0
		        adminApi.getUserByEmail(this.email.trim()).then(result => {
					result.json().then(data => {
						if (data.length > 0) {
							this.users.push(data[0])
							this.isSearched = true
						} else {
							this.isSearched = false
						}
					})
				})
			},
			updateUser() {
				this.isSearched = false
				this.searchUser()
				this.isUpdateUser = true
				setTimeout(() => this.isUpdateUser = false, 1)
			}
		}
	}
</script>