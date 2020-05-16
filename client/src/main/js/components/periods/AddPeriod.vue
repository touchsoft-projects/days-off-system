<template>
	<div class="containerAdd">
		<p id="labelNewPeriod">New period</p>
		<table class="table">
			<tr>
				<th>Start Date</th><td><input type="date" v-model="period.startDate" key="start-date"/></td>
			</tr>
			<tr>
				<th>End Date</th><td><input type="date" v-model="period.endDate" key="end-date"/></td>
			</tr>
			<tr>
				<th>Period Type</th>
				<td>
					<select v-model="period.periodType" key="period-type">
						<option value="VACATION">Vacation</option>
						<option value="DAY_OFF">Day off</option>
					</select>
				</td>
			</tr>
		</table>
		<input type="button" class="buttonSave" value="Save" @click="save"/>
		<p v-show="isShowMessage">{{ message }}</p>
	</div>
</template>

<script type="text/javascript">
	import { commonFunctions } from 'mixins/CommonFunctions.js'
	import { mapGetters } from 'vuex'
	import * as MESSAGES from 'constants/messages.js'
	export default {
		mixins: [ commonFunctions ],
		data() {
			return {
				period: {
					userId: ''
				}
			}
		},
		computed: {
			...mapGetters({
                  userProfile: 'getUser',
                  adminProfile: 'adminStore/getUser'
            })
		},
		methods: {
			save() {
				this.period.id = ''
				this.period.userId = this.isAdmin() ? this.adminProfile.id : this.userProfile.id
				const payload = {
					period: Object.assign({}, this.period),
					checkResult: this.checkResult,
					successMessage: MESSAGES.PERIOD_ADDED_SUCCESS,
					failureMessage: MESSAGES.PERIOD_ADDED_FAILURE	
				}
				if (this.isAdmin()) {
					this.$store.dispatch("adminStore/addPeriodAction", payload)
				} else {
					this.$store.dispatch("userStore/addPeriodAction", payload)
				}
			},
		},
	}
</script>