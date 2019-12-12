	<!DOCTYPE html>
	<html>
	<head>
		<meta charset="UTF-8">
		<title>Logged in</title>
		<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/vue-resource@1.5.1"></script>
		<style>
			.tab-button {
				width: 100px;
				height: 40px;
				padding: 6px 10px;
				border-top-left-radius: 3px;
				border-top-right-radius: 3px;
				border: 1px solid #ccc;
				cursor: pointer;
				background: #f0f0f0;
				margin-bottom: 0px;
				margin-right: 10px;
			}
			.tab-button:hover {
				background: #e0e0e0;
			}
			.tab-button.active {
				background: #e0e0e0;
			}
			.tab {
				border: 1px solid #ccc;
				padding: 10px;
			}

			#top {
				height: 40px;
				/*border: 3px solid red;*/
			}

			#inTop {
				width: 80px;
				float: right;
				padding: 10px;
			}

			a {
				margin: 10px;
			}

			#left {
				float: left;
				width: 120px;
				/*border: 2px solid black;*/

			}

			.table {
				table-layout: fixed;
				width: 100%;
				border-collapse: collapse;
				/*border: 3px solid purple;*/
			}

			td:nth-child(2) {
				/*border: 1px solid black;*/
			}

			#inLeft {
				margin: 5px;
				width: 110px;
			}

			#right {
				float: left;
				width: 600px;
				height: 400px;
				border-left: 1px solid #183533;
			}

			#component {
				border: 0px solid black;
			}
		</style>

	</head>
	<body>

	<header id="top-component-demo">

	</header>

	<div id="dynamic-component-demo" class="demo">
		<div id="left">
			<div id="inLeft">
				<button
					v-for="tab in tabs"
					v-bind:key="tab.name"
					v-bind:class="['tab-button', { active: currentTab.name === tab.name }]"
					v-on:click="currentTab = tab">
					{{ tab.name }}
				</button>
			</div>
		</div>

		<div id="right">
			<component id="component"
					v-bind:is="currentTab.component"
					class="tab"
			></component>
		</div>

		<div id="hh">
		</div>
	</div>



	<script>
		var urlUserApiGetUser = "api/v1/user/getUser";
		var urlUserApiGetPeriods = "api/v1/user/getPeriods";
		var urlUserApiAddPeriod = "api/v1/user/addPeriod";
		var urlUserApiUpdatePeriod = "api/v1/user/updatePeriod";
		var urlUserApiDeletePeriod = "api/v1/user/deletePeriodById";
		var apiGetUser = Vue.resource(urlUserApiGetUser);
		var apiGetPeriods = Vue.resource(urlUserApiGetPeriods);
		var apiAddPeriod = Vue.resource(urlUserApiAddPeriod);
		var apiUpdatePeriod = Vue.resource(urlUserApiUpdatePeriod);
		var apiDeletePeriod = Vue.resource(urlUserApiDeletePeriod);

		var urlAdminApiAddUser = "api/v1/admin/addUser";
		var urlAdminApiUpdateUser = "api/v1/admin/updateUser";
		var urlAdminApiGetUser = "api/v1/admin/getUserById";
		var urlAdminApiGetAll = "api/v1/admin/getAll";
		var urlAdminApiGetPeriods = "api/v1/admin/getPeriodsByUserId";
		var urlAdminApiAddPeriod = "api/v1/admin/addPeriod";
		var urlAdminApiUpdatePeriod = "api/v1/admin/updatePeriod";
		var urlAdminApiDeletePeriod = "api/v1/admin/deletePeriodById";
		var apiAdminAddUser = Vue.resource(urlAdminApiAddUser);
		var apiAdminUpdateUser = Vue.resource(urlAdminApiUpdateUser);
		var apiAdminGetUserById = Vue.resource(urlAdminApiGetUser);
		var apiAdminGetAll = Vue.resource(urlAdminApiGetAll);
		var apiAdminGetPeriodsByUserId = Vue.resource(urlAdminApiGetPeriods);
		var apiAdminAddPeriod = Vue.resource(urlAdminApiAddPeriod);
		var apiAdminUpdatePeriod = Vue.resource(urlAdminApiUpdatePeriod);
		var apiAdminDeletePeriodById = Vue.resource(urlAdminApiDeletePeriod);
		var user = {};

		getUserInfo();

		var tabsEmployee = [
			{
				name: 'Info',
				component: 'info'
			},
			{
				name: 'Periods',
				component: 'periods-wrap'
			},
			{
				name: 'AddPeriod',
				component: 'add-period-wrap'
			}
		]

		var tabsAdmin = [
			{
				name: 'AddUser',
				component: 'add-user-wrap'
			},
			{
				name: 'GetUser',
				component: 'get-user-wrap'
			},
			{
				name: 'GetUsers',
				component: 'get-users-wrap'
			},
		]

		Vue.component('top', {
			data: function() {
				return {
					user: user,
				};
			},
			template:
					'<div id="top">' +
						'<div id="inTop">' +
							'<span>{{ user.firstName }}</span>' +
							'<a href="logout">logout</a>' +
						'</div>' +
					'</div>',
		});

		Vue.component('info', {
			data: function() {
				return {
					user: user,
				};
			},
			template:
					'<div id="right">' +
						'<table class="table">' +
							'<tr><td>Name</td><td><p>{{ fullName }}</p></td></tr>' +
							'<tr><td>Email</td><td><p>{{ user.email }}</p></td></tr>' +
							'<tr><td>Passport Id</td><td><p>{{ user.passportId }}</p></td></tr>' +
						'</table>' +
					'</div>',
			computed: {
				fullName: function(){
					return [user.firstName, user.secondName].join(" ")
				},
			},

		});

		Vue.component('periods-wrap', {
			data: function() {
				return {
					userId: user.id,
					urlApiGet: urlUserApiGetPeriods,
					urlApiUpdate: urlUserApiUpdatePeriod,
					urlApiDelete: urlUserApiDeletePeriod,
				};
			},
			template:
					'<div id="right">' +
						'<periods ' +
							':userId="userId" ' +
							':urlApiGet="urlApiGet" ' +
							':urlApiUpdate="urlApiUpdate" ' +
							':urlApiDelete="urlApiDelete"/>' +
					'</div>',
		});

		Vue.component('periods', {
		    props: ["userId", "urlApiGet", "urlApiUpdate", "urlApiDelete"],
			data: function() {
				return {
					periods: [],
					isEditedPeriod: false,
					period: {},
					indexEditedPeriod: 0,
					message: "",
					isShowMessage: false,
				};
			},
			template: '<div>' +
						'<table>' +
							'<tr>' +
								'<th>&#8470</th>' + /*sybmol №*/
								'<th>Start Date</th>' +
								'<th>End Date</th>' +
								'<th>Type Period</th>' +
							'</tr>' +
							'<tr v-for="(period, index) in periods" :key="period.id">' +
								'<td>{{ index + 1 }}</td>' +
								'<td>{{ period.startDate }}</td>' +
								'<td>{{ period.endDate }}</td>' +
								'<td>{{ period.periodType }}</td>' +
								'<td><input type="button" value="Edit" @click="edit(period, index + 1)" /></td>' +
								'<td><input type="button" value="Delete" @click="del(period)" /></td>' +
							'</tr>' +
						'</table>' +
						'<p v-if="isEditedPeriod">&#8470{{ indexEditedPeriod }}</p>' +
						'<div v-if="isEditedPeriod">' +
							'<add-edit-period ' +
								':isNewPeriod="false" ' +
								':editedPeriod="period" ' +
								'v-on:update-period="saveEdited"/>' +
						'</div>' +
						'<div v-show="isShowMessage">' +
							'<p>{{ message }}</p>' +
						'</div>' +
					'</div>',
			created: function() {
				var apiGet = Vue.resource(this.urlApiGet);
				apiGet.get({id: this.userId}).then(result =>
						result.json().then(data =>
							data.forEach(period => this.periods.push(period))
						)
				)
			},
			methods: {
				edit: function(period, number) {
				    this.indexEditedPeriod = number;
					this.hideMessage();
					this.isEditedPeriod = false;
					this.period = Object.assign({}, period);
					setTimeout(() => this.isEditedPeriod = true, 0);
				},

				saveEdited: function(period) {
					this.period = Object.assign({}, period);
					index = getIndex(this.periods, this.period.id);
					if (index > -1) {
						var apiUpdate = Vue.resource(this.urlApiUpdate);
						apiUpdate.update(this.period).then(result => {
							if (result.ok) {
								this.periods[index] = this.period;
								this.isEditedPeriod = false;
								this.period = {};
							}
						})
						this.showMessage("Changing saved!");
					} else {
						this.showMessage("Changing don't save!");
						this.isEditedPeriod = false;
					}
				},

				del: function(period) {
					var apiDelete = Vue.resource(this.urlApiDelete);
					apiDelete.remove({id: period.id}).then(result => {
						if (result.ok) {
							this.periods.splice(this.periods.indexOf(period), 1)
						}
						this.isShowMessage = false;
					})
				},

				showMessage: function(message) {
					this.message = message;
					this.isShowMessage = true;
				},

				hideMessage: function() {
					this.message = "";
					this.isShowMessage = false;
				},
			}
		});

		Vue.component('add-period-wrap', {
			data: function() {
				return {
					isNewPeriod: true,
				    period: {
				        userId: user.id,
					},
					urlUserApiAddPeriod: urlUserApiAddPeriod,
				};
			},
			template:
					'<div id="right">' +
						'<add-edit-period ' +
							':isNewPeriod="isNewPeriod" ' +
							':editedPeriod="period" ' +
							':urlApiSave="urlUserApiAddPeriod"/>' +
					'</div>',
		});

		Vue.component('add-edit-period', {
			props: ["isNewPeriod", "editedPeriod", "urlApiSave"],
			data: function() {
				return {
					period: {},
					message: "",
					isShowMessage: false,
				};
			},
			template:
					'<div>' +
						'<div>' +
							'<table class="table">' +
								'<tr><td>Start Date</td><td><input type="date" v-model="period.startDate" key="start-date"/></td></tr>' +
								'<tr><td>End Date</td><td><input type="date" v-model="period.endDate" key="end-date"/></td></tr>' +
								'<tr><td>Period Type</td><td>' +
									'<select v-model="period.periodType" key="period-type">' +
									'<option value="VACATION">Vacation</option>' +
									'<option value="DAY_OFF">Day off</option>' +
									'</select>' +
								'</td></tr>' +
							'</table>' +
							'<input type="button" value="Save" @click="isNewPeriod ? save() : update()"/>' +
						'</div>' +
						'<div v-show="isShowMessage">' +
							'<p>{{ message }}</p>' +
						'</div>' +
					'</div>',
			created: function() {
				this.period = Object.assign({}, this.editedPeriod);
			},
			methods: {
				save: function() {
					var apiSave = Vue.resource(this.urlApiSave);
					apiSave.save(this.period).then(result => {
						if (result.ok) {
							this.showMessage("Period was added!");
						} else {
							this.showMessage("Period wasn't added!");
						}
					})
				},

				update: function() {
					console.log("update period");
					this.$emit('update-period', this.period);
				},

				showMessage: function(message) {
					this.message = message;
					this.isShowMessage = true;
				},

				hideMessage: function() {
					this.message = "";
					this.isShowMessage = false;
				},
			},

		});

		Vue.component('add-user-wrap', {
			data: function() {
				return {
					isNewUser: true,
				};
			},
			template:
					'<div id="right">' +
						'<add-edit-user :isNewUser="isNewUser"/>' +
					'</div>',
		});

		Vue.component('add-edit-user', {
		    props: ["isNewUser", "editedUser"],
			data: function() {
				return {
					user: {
					    roles: [ "" ]
					},
					message: "",
					isShowMessage: false,
				};
			},
			template:
					'<div>' +
						'<div>' +
							'<table class="table">' +
								'<tr><td>First Name</td><td><input type="text" v-model="user.firstName"/></td></tr>' +
								'<tr><td>Second Name</td><td><input type="text" v-model="user.secondName"/></td></tr>' +
								'<tr><td>Last Name</td><td><input type="text" v-model="user.lastName"/></td></tr>' +
								'<tr><td>Passport Id</td><td><input type="text" v-model="user.passportId"/></td></tr>' +
								'<tr><td>Email</td><td><input type="text" v-model="user.email"/></td></tr>' +
								'<tr><td>Password</td><td><input type="text" v-model="user.password"/></td></tr>' +
								'<tr><td>Role</td><td>' +
									'<select v-model="user.roles[0]">' +
										'<option value="ROLE_EMPLOYEE">Employee</option>' +
										'<option value="ROLE_ADMIN">Admin</option>' +
									'</select></td></tr>' +
								'<tr><td>Enabled</td><td><input type="checkbox" v-model="user.enabled"/></td></tr>' +
							'</table>' +
							'<input type="button" value="Save" @click="isNewUser ? save() : update()"/>' +
						'</div>' +
						'<div v-show="isShowMessage">' +
							'<p>{{ message }}</p>' +
						'</div>' +
					'</div>',
			created: function() {
		        if (!this.isNewUser) {
					this.user = Object.assign({}, this.editedUser);
				}
			},
			methods: {
				save: function() {
				    console.log("save");
					apiAdminAddUser.save(this.user).then(result => {
						if (result.ok) {
							this.showMessage("User was added!");
						} else {
							this.showMessage("User wasn't added!");
						}
					})
				},

				update: function() {
					console.log("update");
					this.$emit('update-user', this.user);
				},

				showMessage: function(message) {
					this.message = message;
					this.isShowMessage = true;
				},

				hideMessage: function() {
					this.message = "";
					this.isShowMessage = false;
				},
			},

		});

		Vue.component('get-user-wrap', {
			data: function() {
				return {
					users: [],
					isSearched: false,
					id: "",
				};
			},
			template:
					'<div id="right">' +
						'<div>' +
							'<input type="text" v-model="id"/>' +
							'<input type="button" value="Search" @click="searchUser" />' +
						'</div>' +
						'<users v-show="isSearched" :users="users"/>' +
					'</div>',
			methods: {
			    searchUser: function() {
			        this.users.length = 0;
					apiAdminGetUserById.get({id: this.id}).then(result => {
						result.json().then(data => {
							if (data.length > 0) {
								this.users.push(data[0]);
								this.isSearched = true;
							} else {
								this.isSearched = false;
							}
						})
					})
				}
			}
		});

		Vue.component('get-users-wrap', {
			data: function() {
				return {
					users: [],
				};
			},
			template:
					'<div id="right">' +
						'<users :users="users"/>' +
					'</div>',
			created: function() {
				apiAdminGetAll.get().then(result =>
						result.json().then(data =>
								data.forEach(user => this.users.push(user))
						)
				)
			},
		});

		Vue.component('users', {
		    props: ["users"],
			data: function() {
				return {
					periods: [],
					isEditedUser: false,
					user: {
						roles: [ "" ],
					},
					indexEditedUser: 0,
					message: "",
					isShowMessage: false,
					isShowPeriods: false,
					isShowButtonPeriods: false,
					urlApiGet: urlAdminApiGetPeriods,
					urlApiUpdate: urlAdminApiUpdatePeriod,
					urlApiDelete: urlAdminApiDeletePeriod,
				};
			},
			template:
					'<div>' +
						'<div>' +
							'<table>' +
								'<tr>' +
									'<th v-show="users.length > 1 ? true : false">&#8470</th>' + /*sybmol №*/
									'<th>Id</th>' +
									'<th>First Name</th>' +
									'<th>Second Name</th>' +
									'<th>Last Name</th>' +
									'<th>Email</th>' + /*sybmol №*/
									'<th>Passport Id</th>' +
									'<th>Roles</th>' +
									'<th>Enabled</th>' +
								'</tr>' +
								'<tr v-for="(user, index) in users" :key="user.id">' +
									'<td v-show="users.length > 1 ? true : false">{{ index + 1 }}</td>' +
									'<td>{{ user.id }}</td>' +
									'<td>{{ user.firstName }}</td>' +
									'<td>{{ user.secondName }}</td>' +
									'<td>{{ user.lastName }}</td>' +
									'<td>{{ user.email }}</td>' +
									'<td>{{ user.passportId }}</td>' +
									'<td>{{ user.roles[0] }}</td>' +
									'<td>{{ user.enabled }}</td>' +
									'<td><input type="button" value="Edit" @click="edit(user, index + 1)" /></td>' +
									'<td><input type="button" value="Delete" @click="del(user)" /></td>' +
								'</tr>' +
							'</table>' +
						'</div>' +
						'<div v-if="isEditedUser">' +
							'<add-edit-user :isNewUser="false" :editedUser="user" v-on:update-user="saveEdited"/>' +
						'</div>' +
						'<input type="button" value="ShowPeriods" @click="show" v-if="isShowButtonPeriods"/>' +
						'<periods ' +
							'v-if="isShowPeriods" ' +
							':userId="user.id" ' +
							':urlApiGet="urlApiGet" ' +
							':urlApiUpdate="urlApiUpdate" ' +
							':urlApiDelete="urlApiDelete"/>' +
					'</div>',
			methods: {
				show: function() {
					this.isShowPeriods = true;
				},

				edit: function(user, number) {
					this.indexEditedUser = number;
					this.hideMessage();
					this.isEditedUser = false;
					this.user = Object.assign({}, user);
					setTimeout(() => this.isEditedUser = true, 0);
					this.isShowPeriods = false;
					this.isShowButtonPeriods = true;
				},

				saveEdited: function(user) {
					this.user = Object.assign({}, user);
					index = getIndex(this.users, this.user.id);
					if (index > -1) {
						apiAdminUpdateUser.update(this.user).then(result => {
							if (result.ok) {
								this.users[index] = this.user;
								this.isEditedUser = false;
								this.user = {};
							}
						})
						this.showMessage("Changing saved!");
					} else {
						this.showMessage("Changing don't save!");
						this.isEditedUser = false;
					}
					console.log(user);
					this.isShowButtonPeriods = false;
					this.isShowPeriods = false;
				},

				del: function(period) {
					apiDeletePeriod.remove({id: period.id}).then(result => {
						if (result.ok) {
							this.periods.splice(this.periods.indexOf(period), 1)
						}
						this.isShowMessage = false;
					})
				},

				showMessage: function(message) {
					this.message = message;
					this.isShowMessage = true;
				},

				hideMessage: function() {
					this.message = "";
					this.isShowMessage = false;
				},
			}
		});


		//functions declaring
		function createVue(tabs) {
			new Vue({
				el: '#dynamic-component-demo',
				data: {
					tabs: tabs,
					currentTab: tabs[0]
				},
			})

			new Vue({
				el: '#top-component-demo',
				template: '<top/>'
			})
		}

		function getUserInfo() {
			var xhr = new XMLHttpRequest();
			xhr.open('GET', "api/v1/user/getUser", true);
			xhr.send();
			xhr.onreadystatechange = function() {
				if (xhr.readyState != 4) return;
				if (xhr.status == 200) {
					user = JSON.parse(xhr.responseText)[0];
					console.log(user);
					if (user.roles[0] == "ROLE_EMPLOYEE") {
						createVue(tabsEmployee);
					} else {
						createVue(tabsAdmin);
					}

				}
			}
		}

		function getIndex(list, id) {
			for (var i = 0; i < list.length; i++ ) {
				if (list[i].id === id) {
					return i;
				}
			}
			return -1;
		}

	</script>
	</body>
	</html>
