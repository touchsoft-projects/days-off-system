<template>
	<div>
		<table class="table">
			<tr><th>Start Date</th><td><slot name="startDate"></slot></td></tr>
			<tr><th>End Date</th><td><slot name="endDate"></slot></td></tr>
			<tr><th>Period Type</th><td><slot name="periodType"></slot></td></tr>
		</table>
		<input type="button" value="Save" @click="update" class="buttonSave"/>
	</div>
</template>

<script type="text/javascript">
	export default {
		methods: {
			update() {
				this.$emit('update-period')
			},
		},
	}
</script>