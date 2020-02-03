<template>
	<div class="parent">
		<div v-if="isEditedPeriod" class="rightColumn">
			<p id="labelPeriodNumber">Edited period № {{ indexEditedPeriod }}</p>
			<edit-period
				v-on:update-period="update">
				<input type="date" v-model="period.startDate" key="start-date" slot="startDate">
					{{ period.startDate }}
				</input>
				<input type="date" v-model="period.endDate" key="end-date" slot="endDate">
					{{ period.endDate }}
				</input>
				<select v-model="period.periodType" key="period-type" slot="periodType">
					<option value="VACATION">Vacation</option>
					<option value="DAY_OFF">Day off</option>
				</select>
			</edit-period>
			<div v-show="isShowMessage">
				<p>{{ message }}</p>
			</div>
		</div>
		<div class="leftColumn">
			<table class="table">
				<tr>
					<th>№</th>
					<th>Start Date</th>
					<th>End Date</th>
					<th>Type Period</th>
				</tr>
				<tr v-for="(period, index) in periods" :key="period.id">
					<td>{{ index + 1 }}</td>
					<td>{{ period.startDate }}</td>
					<td>{{ period.endDate }}</td>
					<td>{{ periodType(period.periodType) }}</td>
					<td class='backgroundWhite'><input type="button" value="Edit" @click="edit(period, index + 1)" /></td>
					<td class='backgroundWhite'><input type="button" value="Delete" @click="remove(period, index + 1)" /></td>
				</tr>
			</table>
		</div>
	</div>
</div>
</template>

<script type="text/javascript">
	import EditPeriod from 'components/periods/EditPeriod.vue'
	import { commonFunctions } from 'mixins/CommonFunctions.js'
	import { mapGetters } from 'vuex'
	import * as MESSAGES from 'constants/messages.js'
	import {DAY_OFF, VACATION} from 'constants/common.js'
	export default {
		components: {
			EditPeriod,
		},
		props: [ 'userId' ],
		mixins: [ commonFunctions ],
		data() {
			return {
				isEditedPeriod: false,
				period: {},
				indexEditedPeriod: 0
			}
		},
		mounted() {
			if (this.isAdmin()) {
				this.$store.dispatch("adminStore/getPeriodsAction", {
					userId: this.userId,
				})
			} else {
				this.$store.dispatch("userStore/getPeriodsAction")
			}
		},
		computed: {
			periods() {
				if (this.isAdmin()) {
					return this.$store.getters['adminStore/getPeriods']
				} else {
					return this.$store.getters['userStore/getPeriods'] 
				}
            },
            periodType() {
            	return periodType => this.getPeriodType(periodType)
            },
		},
		methods: {
			getPeriodType(periodType) {
				switch(periodType) {
					case DAY_OFF: return 'Day off';
					case VACATION: return 'Vacation';
				}
			},
			edit(period, number) {
				this.hideMessage()
			    this.indexEditedPeriod = number
			    this.isEditedPeriod = true
				this.period = Object.assign({}, period)
			},
			update() {
				const payload = {
					period: this.period,
					checkResult: this.checkResult,
					successMessage: MESSAGES.PERIOD_UPDATED_SUCCESS,
					failureMessage: MESSAGES.PERIOD_UPDATED_FAILURE	
				}
				if (this.isAdmin()) {
					this.$store.dispatch("adminStore/updatePeriodAction", payload)
				} else {
					this.$store.dispatch("userStore/updatePeriodAction", payload)
				}
			},
			remove(period, index) {
				var isDelete = confirm("You really want to delete the period №" + index + " ?")
				if (isDelete) {
					const payload = {
						id: period.id, 
						checkResult: this.checkResult
					}
					if (this.isAdmin()) {
						this.$store.dispatch("adminStore/removePeriodAction", payload)
					} else {
						this.$store.dispatch("userStore/removePeriodAction", payload)
					}
					if (this.period.id === period.id) {
						this.isEditedPeriod = false
					}
					if (index < this.indexEditedPeriod) {
						this.indexEditedPeriod--
					}
				}
			},
		}
	}
</script>