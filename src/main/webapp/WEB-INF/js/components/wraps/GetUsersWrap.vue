<template>
	<div id="right">
		<users :users="users"/>
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
			}
		},
		created() {
			adminApi.getUsers().then(result =>
				result.json().then(data =>
					data.forEach(user => this.users.push(user))
				)
			)
		},
	}
</script>