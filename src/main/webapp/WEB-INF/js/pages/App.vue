<template>
     <div>
        <top-element>
            <span id="userName" slot="userFirstName">{{ user.firstName }}</span>
        </top-element>
        <div id="side">
            <button
                v-for="tab in tabs"
                v-bind:key="tab.name"
                v-bind:class="['tab-button', { active: currentTab.name === tab.name }]"
                v-on:click="switchTab(tab)">
                {{ tab.name }}
            </button>
        </div>
        <div id="content">
            <component 
                v-bind:is="currentTab.component"
                class="tab"
            ></component>
        </div>
    </div>
</template>

<script>   
    var baseUrl = '/days-off-system'
    function addBaseUrl(url) {
    	return baseUrl + url;
    }

    import TopElement from 'components/TopElement.vue'
    import Info from 'components/Info.vue'
    import Periods from 'components/periods/Periods.vue'
    import AddPeriod from 'components/periods/AddPeriod.vue'
    import AddUser from 'components/users/AddUser.vue'
    import UserWrap from 'components/wraps/UserWrap.vue'
    import UsersWrap from 'components/wraps/UsersWrap.vue'
    import { EventBus } from 'EventBus.js'
    import {mapGetters} from 'vuex'
    export default {
        components: {
            TopElement, Info, Periods, AddPeriod, AddUser, UserWrap, UsersWrap
        },
        data() {
            return {
                user: {},
                profile: '',
                currentTab: {},
                tabs: {},
                tabsEmployee: [
                    {
                        name: 'Info',
                        component: 'Info'
                    },
                    {
                        name: 'Periods',
                        component: 'Periods',
                        updatable: true
                    },
                    {
                        name: 'AddPeriod',
                        component: 'AddPeriod'
                    }
                ],
                tabsAdmin: [
                    {
                        name: 'AddUser',
                        component: 'AddUser'
                    },
                    {
                        name: 'User',
                        component: 'UserWrap',
                        updatable: true
                    },
                    {
                        name: 'Users',
                        component: 'UsersWrap',
                        updatable: true
                    },
                ],
            }
        },
        computed: {
            ...mapGetters([
                  'getProfile',
                  'getUser'
            ])
        },
        watch: {
            getProfile: function(newProfile, oldProfile) {
                this.profile = this.$store.getters['getProfile']
                this.setTabs()
            },
            getUser: function(newUser, oldUser) {
                this.user = newUser
            }
        },
        methods: {
        	setTabs() {
        		if (this.profile == 'EMPLOYEE') {
                    this.tabs = this.tabsEmployee
                } else {
                    this.tabs = this.tabsAdmin
                }
        	},
            switchTab(tab) {
                if (this.currentTab.name === tab.name && this.currentTab.updatable) {
                    if (this.currentTab.name === 'User') {
                        EventBus.$emit('update-data')
                    } else {
                        this.currentTab = {}
                    }
                }
                setTimeout(() => this.currentTab = tab, 20)
            },
        },
    }
</script>

<style src='styles/app.css'></style>