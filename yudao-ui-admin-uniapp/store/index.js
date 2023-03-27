import Vue from 'vue'
import Vuex from 'vuex'
import user from '@/store/modules/user'
import dict from '@/store/modules/dict'
import nickName from '@/store/modules/nickName'
import getters from './getters'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    user,
	dict,
	nickName
  },
  getters
})

export default store
