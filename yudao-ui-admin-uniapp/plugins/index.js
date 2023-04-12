import tab from './tab'
import auth from './auth'
import modal from './modal'
import useDirect from './dictTranslate'
import useStatus from './commonStatus.js'
import useName from './nickName.js'
import dept from './dept.js'
import post from './post.js'

import processDefiend from './processDefiend.js'
export default {
  install(Vue) {
    // 页签操作
    Vue.prototype.$tab = tab
    // 认证对象
    Vue.prototype.$auth = auth
    // 模态框对象
    Vue.prototype.$modal = modal
	useDirect(Vue)
	useStatus(Vue)
	useName(Vue)
	dept(Vue)
	post(Vue)
	processDefiend(Vue)
  }
}
