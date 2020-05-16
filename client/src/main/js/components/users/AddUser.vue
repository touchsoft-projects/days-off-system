<template>
	<div class="containerAdd">
		<table class="table">
			<tr><th>First Name</th><td><input type="text" v-model="user.firstName"/></td></tr>
			<tr><th>Second Name</th><td><input type="text" v-model="user.secondName"/></td></tr>
			<tr><th>Last Name</th><td><input type="text" v-model="user.lastName"/></td></tr>
			<tr><th>Passport Id</th><td><input type="text" v-model="user.passportId"/></td></tr>
			<tr><th>Email</th><td><input type="text" v-model="user.email"/></td></tr>
			<tr><th>Password</th><td><input type="text" v-model="user.password"/></td></tr>
			<tr><th>Role</th><td>
				<select v-model="user.roles[0]">
					<option value="ROLE_EMPLOYEE">Employee</option>
					<option value="ROLE_ADMIN">Admin</option>
				</select></td></tr>
			<tr><th>Enabled</th><td><input type="checkbox" v-model="user.enabled"/></td></tr>
		</table>
		<input type="button" class="buttonSave" value="Save" @click="save"/>
		<p v-show="isShowMessage">{{ message }}</p>
	</div>
</template>

<script type="text/javascript">
	import { commonFunctions } from 'mixins/CommonFunctions.js'
	import adminApi from 'api/admin'
	import * as MESSAGES from 'constants/messages.js'
	export default {
		mixins: [ commonFunctions ],
		data() {
			return {
				user: {
				    roles: [ "" ]
				},
			}
		},
		methods: {
			save() {
				adminApi.addUser(this.user).then(result => {
					this.checkResult(result, MESSAGES.USER_ADDED_SUCCESS, MESSAGES.USER_ADDED_FAILURE)
				})
			},
		},
	}
</script>