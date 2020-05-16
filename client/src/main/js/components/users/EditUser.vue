<template>
	<div>
		<table class="table">
			<tr><th>First Name</th><td><slot name="firstName"></slot></td></tr>
			<tr><th>Second Name</th><td><slot name="secondName"></slot></td></tr>
			<tr><th>Last Name</th><td><slot name="lastName"></slot></td></tr>
			<tr><th>Passport Id</th><td><slot name="passportId"></slot></td></tr>
			<tr><th>Email</th><td><slot name="email"></slot></td></tr>
			<tr><th>Password</th><td><slot name="password"></slot></td></tr>
			<tr><th>Role</th><td><slot name="role"></slot></td></tr>
			<tr><th>Enabled</th><td><slot name="enabled"></slot></td></tr>
		</table>
		<input type="button" value="Save" @click="update" class="buttonSave"/>
	</div>
</template>

<script type="text/javascript">
	export default {
		methods: {
			update() {
				this.$emit('update-user')
			},
		},
	}
</script>