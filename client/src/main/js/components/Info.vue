<template>
	<div>
		<table class="table info">
			<tr><th>Name</th><td>{{ fullName }}</td></tr>
			<tr><th>Email</th><td>{{ getUser.email }}</td></tr>
			<tr><th>Passport Id</th><td>{{ getUser.passportId }}</td></tr>
		</table>
	</div>
</template>

<script type="text/javascript">
	import { mapGetters } from 'vuex'
	export default {
		data() {
			return {
				user: {},
			}
		},
		computed: {
			...mapGetters([
                  'getUser'
            ]),
			fullName() {
				return [this.getUser.firstName, this.getUser.secondName].join('')
			}
		},
	}
</script>
