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
		var apiGetUser = Vue.resource("api/v1/user/getUser");
		var apiGetPeriods = Vue.resource("api/v1/user/getPeriods");
		var apiDeletePeriod = Vue.resource("api/v1/user/deletePeriodById");
		var apiAddPeriod = Vue.resource("api/v1/user/addPeriod");
		var apiUpdatePeriod = Vue.resource("api/v1/user/updatePeriod");

		var apiAdminAddUser = Vue.resource("api/v1/admin/addUser");
		var apiAdminUpdateUser = Vue.resource("api/v1/admin/UpdateUser");
		var apiAdminGetUserById = Vue.resource("api/v1/admin/getUserById");
		var apiAdminGetAll = Vue.resource("api/v1/admin/getAll");
		var apiAdminGetPeriodsByUserId = Vue.resource("api/v1/admin/getPeriodsByUserId");
		var apiAdminAddPeriod = Vue.resource("api/v1/admin/addPeriod");
		var apiAdminUpdatePeriod = Vue.resource("api/v1/admin/updatePeriod");
		var apiAdminDeletePeriodById = Vue.resource("api/v1/admin/deletePeriodById");
		var user = {};

		getUserInfo();

		var tabsEmployee = [
			{
				name: 'Info',
				component: 'info'
			},
			{
				name: 'Periods',
				component: 'periods'
			},
			{
				name: 'AddPeriod',
				component: 'add-period'
			}
		]

		var tabsAdmin = [
			{
				name: 'AddUser',
				component: 'add-user'
			},
		]

		Vue.component('top', {
			data: function() {
				return {
					user: user,
				};
			},
			template: '<div id="top">' +
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
			template: '<div id="right">' +
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

		Vue.component('periods', {
			data: function() {
				return {
					periods: [],
					isEdited: false,
					period: {},
					indexEditedPeriod: 0,
					message: "",
					isShowMessage: false,
				};
			},
			template: '<div id="right">' +
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
						'<div v-show="isEdited">' +
							'<p>Edited Period &#8470 {{ indexEditedPeriod }}</p>' +
							'<label>Start Date</label>' +
							'<input type="date" v-model="period.startDate"/>' +
							'<label>End Date</label>' +
							'<input type="date" v-model="period.endDate"/>' +
							'<label>Period Type</label>' +
							'<select v-model="period.periodType">' +
								'<option value="VACATION">Vacation</option>' +
								'<option value="DAY_OFF">Day off</option>' +
							'</select>' +
							'<td><input type="button" value="Save" @click="saveEdited()"/></td>' +
					    '</div>' +
						'<div v-show="isShowMessage">' +
							'<p>{{ message }}</p>' +
						'</div>' +
					'</div>',
			created: function() {
				apiGetPeriods.get().then(result =>
						result.json().then(data =>
							data.forEach(period => this.periods.push(period))
						)
				)
			},
			methods: {
				edit: function(period, number) {
				    this.indexEditedPeriod = number;
					this.hideMessage();
					this.isEdited = true;
					this.period = Object.assign({}, period);
				},

				saveEdited: function() {
					index = getIndex(this.periods, this.period.id);
					if (index > -1) {
					    // this.period.userId = user.id;
						apiUpdatePeriod.update(this.period).then(result => {
							if (result.ok) {
								this.periods[index] = this.period;
								this.isEdited = false;
								this.period = {};
							}
						})
						this.showMessage("Changing saved!");
					} else {
						this.showMessage("Changing don't save!");
						this.isEdited = false;
					}
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

		Vue.component('add-period', {
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
							'<label>Start Date</label>' +
							'<input type="date" v-model="period.startDate"/>' +
							'<label>End Date</label>' +
							'<input type="date" v-model="period.endDate"/>' +
							'<label>Period Type</label>' +
							'<select v-model="period.periodType">' +
								'<option value="VACATION">Vacation</option>' +
								'<option value="DAY_OFF">Day off</option>' +
							'</select>' +
							'<td><input type="button" value="Save" @click="save"/></td>' +
						'</div>' +
						'<div v-show="isShowMessage">' +
							'<p>{{ message }}</p>' +
						'</div>' +
					'</div>',
			methods: {
				save: function() {
					this.period.userId = user.id;
					apiAddPeriod.save(this.period).then(result => {
						if (result.ok) {
							result.json().then(id => {
								this.period.id = id;
							})
							this.showMessage("Period was added!");
						} else {
							this.showMessage("Period wasn't added!");
						}
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
			},

		});

		Vue.component('add-user', {
			data: function() {
				return {
					user: {
					    roles: [ "" ]
					},
					message: "",
					isShowMessage: false,
					t: "checkbox",
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
							'<input type="button" value="Save" @click="save"/>' +
						'</div>' +
						'<div v-show="isShowMessage">' +
							'<p>{{ message }}</p>' +
						'</div>' +
					'</div>',
			methods: {
				save: function() {
					apiAdminAddUser.save(this.user).then(result => {
						if (result.ok) {
							this.showMessage("User was added!");
						} else {
							this.showMessage("User wasn't added!");
						}
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
			},

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
