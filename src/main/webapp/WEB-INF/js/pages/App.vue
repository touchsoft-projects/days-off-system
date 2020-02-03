<template>
     <div>
        <top-element>
                <span id="userName" slot="userFirstName">{{ user.firstName }}</span>
        </top-element>
        <div id="side">
            <router-link 
                v-for="tab in tabs"
                :key="tab.link"
                :to="tab.name"
                tag="button" 
                class="tab-button" 
            >{{ tab.title }}
            </router-link>
        </div>
        <div id="content">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>   
    var baseUrl = '/days-off-system'
    function addBaseUrl(url) {
    	return baseUrl + url;
    }

    import TopElement from 'components/TopElement.vue'
    import {mapGetters} from 'vuex'
    export default {
        components: {
            TopElement,
        },
        data() {
            return {
                user: {},
                profile: '',
                tabs: {},
                tabsEmployee: [
                    {
                        title: 'Info',
                        link: addBaseUrl('/info'),
                        name: 'info'
                    },
                    {
                        title: 'Periods',
                        link: addBaseUrl('/periods'),
                        name: 'periods'
                    },
                    {
                        title: 'AddPeriod',
                        link: addBaseUrl('/addPeriod'),
                        name: 'addPeriod'
                    }
                ],
                tabsAdmin: [
                    {
                        title: 'AddUser',
                        link: addBaseUrl('/addUser'),
                        name: 'addUser'
                    },
                    {
                        title: 'GetUser',
                        link: addBaseUrl('/getUser'),
                        name: 'getUser'
                    },
                    {
                        title: 'GetUsers',
                        link: addBaseUrl('/getUsers'),
                        name: 'getUsers'
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
        	}
        },
    }
</script scoped>

<style src='styles/app.css'></style>