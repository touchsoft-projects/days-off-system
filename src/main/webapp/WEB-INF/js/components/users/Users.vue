<template>
	<div>
		<div class="parent">
			<div v-if="isEditedUser" class="rightColumn">
				<edit-user v-on:update-user="saveEdited">
					<input type="text" v-model="user.firstName" key="first-name" slot="firstName">
						{{ user.firstName }}
					</input> 
					<input type="text" v-model="user.secondName" key="second-name" slot="secondName">
						{{ user.secondName }}
					</input>
					<input type="text" v-model="user.lastName" key="last-name" slot="lastName">
						{{ user.lastName }}
					</input>
					<input type="text" v-model="user.passportId" key="passport-id" slot="passportId">
						{{ user.passportId }}
					</input>
					<input type="text" v-model="user.email" key="email" slot="email">
						{{ user.email }}
					</input>
					<input type="text" v-model="user.password" key="password" slot="password">
						{{ user.password }}
					</input>
					<select v-model="user.roles[0]" key="role" slot="role">
						<option value="ROLE_ADMIN">Admin</option>
						<option value="ROLE_EMPLOYEE">Employee</option>
					</select>
					<input type="checkbox" v-model="user.enabled" key="enabled" slot="enabled">
						{{ user.enabled }}
					</input>
				</edit-user>
				<input type="button" value="Show periods" ref="showPeriods" @click="isShowPeriods ? hidePeriods() : showPeriods()" v-if="isShowButtonPeriods" id="buttonShowPeriods"/>
				<div v-show="isShowMessage">
					<p>{{ message }}</p>
				</div>
			</div>
			<div class="leftColumn">
				<table class="table">
					<tr>
						<th v-show="users.length > 1 ? true : false">№</th>
						<th>Id</th>
						<th>First Name</th>
						<th>Second Name</th>
						<th>Last Name</th>
						<th>Email</th>
						<th>Passport Id</th>
						<th>Roles</th>
						<th>Enabled</th>
					</tr>
					<tr v-for="(user, index) in users" :key="user.id">
						<td v-show="users.length > 1 ? true : false">{{ index + 1 }}</td>
						<td>{{ user.id }}</td>
						<td>{{ user.firstName }}</td>
						<td>{{ user.secondName }}</td>
						<td>{{ user.lastName }}</td>
						<td>{{ user.email }}</td>
						<td>{{ user.passportId }}</td>
						<td>{{ userRole(user.roles[0]) }}</td>
						<td>{{ user.enabled }}</td>
						<td class='backgroundWhite'><input type="button" value="Edit" @click="edit(user, index + 1)" /></td>
						<td class='backgroundWhite'><input type="button" value="Delete" @click="remove(user)" /></td>
					</tr>
				</table>
			</div>
		</div>
		<div id="parentPeriods">
			<input type="button" value="AddPeriod" @click="isAddPeriod ? hideAddPeriod() : addPeriod()"
				v-if="isShowPeriods" ref="addPeriod" id="buttonAddPeriod">
			<h3 v-if="isShowPeriods" id="labelPeriodsFor">Periods for {{ user.email }}</h3>
			<add-period v-if="isAddPeriod" class="rightColumn"/>
			<periods v-if="isShowPeriods" :userId="user.id"/>
		</div>
	</div>
</template>

<script type="text/javascript">
	import { commonFunctions } from 'mixins/CommonFunctions.js'
	import EditUser from 'components/users/EditUser.vue'
	import Periods from 'components/periods/Periods.vue'
	import AddPeriod from 'components/periods/AddPeriod.vue'
	import adminApi from 'api/admin'
	import * as MESSAGES from 'constants/messages.js'
	import {ADMIN, EMPLOYEE} from 'constants/common.js'
	export default {
		components: {
			EditUser, Periods, AddPeriod
		},
		mixins: [ commonFunctions ],
		props: ["users"],
		data() {
			return {
				periods: [],
				isEditedUser: false,
				user: {
					roles: [ "" ],
				},
				indexEditedUser: 0,
				isShowPeriods: false,
				isShowButtonPeriods: false,
				isAddPeriod: false,
				isHide: false,
			}
		},
		computed: {
			userRole() {
				return userRole => this.getUserRole(userRole)
			}
		},
		methods: {
			getUserRole(userRole) {
				switch(userRole) {
					case ADMIN: return 'Admin';
					case EMPLOYEE: return 'Employee';
				}
			},
			showPeriods() {
				this.isShowPeriods = true
				this.$refs.showPeriods.value = 'Hide periods'
			},
			hidePeriods() {
				this.isShowPeriods = false
				this.isAddPeriod = false
				if (this.$refs.showPeriods !== undefined) {
					this.$refs.showPeriods.value = 'Show periods'	
				}
			},
			addPeriod() {
				this.isAddPeriod = true
				this.$refs.addPeriod.value = 'Hide adding period'
			},
			hideAddPeriod() {
				this.isAddPeriod = false
				this.$refs.addPeriod.value = 'AddPeriod'	
			},
			edit(user, number) {
				this.hidePeriods()
				this.isShowButtonPeriods = true
				this.hideMessage()
			    this.indexEditedUser = number
			    this.isEditedUser = true
				this.user = Object.assign({}, user)
				this.$store.commit('adminStore/setUser', this.user)
			},
			saveEdited(user) {
				var updatedUser = Object.assign({}, this.user)
				var index = this.getIndex(this.users, updatedUser.id)
				adminApi.updateUser(updatedUser).then(result => {
					this.checkResult(result, MESSAGES.USER_UPDATED_SUCCESS, MESSAGES.USER_UPDATED_FAILURE)
					this.users[index] = updatedUser
					this.$store.commit('adminStore/setUser', updatedUser)
				})
			},
			remove(user) {
				// var isDelete = confirm("You really want to delete this user?")
			},
		}
	}
</script>