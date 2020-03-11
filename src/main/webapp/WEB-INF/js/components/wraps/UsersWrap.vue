<template>
	<users :users="users"/>
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
			}
		},
		mounted() {
			this.fetchData()
		},
		methods: {
			fetchData() {
				adminApi.getUsers().then(result =>
					result.json().then(data => {
						this.users = []
						data.forEach(user => this.users.push(user))
					})
				)
			}
		},
	}
</script>