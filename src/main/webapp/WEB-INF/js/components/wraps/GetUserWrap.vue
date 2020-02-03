<template>
	<div id="right">
		<div>
			<label>Search by email: </label>
			<input type="text" v-model="email"/>
			<input type="button" value="Search" @click="searchUser" />
		</div>
		<users v-show="isSearched" :users="users"/>
	</div>
</template>

<script type="text/javascript">
	import Users from 'components/users/Users.vue'
	import adminApi from 'api/admin'
	export default {
		components: {
			Users,
		},
		data() {
			return {
				users: [],
				isSearched: false,
				email: "",
			}
		},
		methods: {
		    searchUser() {
		        this.users.length = 0
		        adminApi.getUserByEmail(this.email).then(result => {
					result.json().then(data => {
						if (data.length > 0) {
							this.users.push(data[0])
							this.isSearched = true
						} else {
							this.isSearched = false
						}
					})
				})
			}
		}
	}
</script>