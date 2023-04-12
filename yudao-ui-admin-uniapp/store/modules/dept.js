import {
	listSimpleDeptApi
} from '@/api/work/dept.js'

const dept = {
	state: {
		dept: {}
	},
	mutations: {
		INIT_DEPT: (state,data) => {
			state.dept = data
		}
	},

	actions: {
		// 获得字典数据
		InitDept({
			commit
		}) {
			return new Promise((resolve, reject) => {
				listSimpleDeptApi().then(res => {
					res = res.data
					commit('INIT_DEPT', res)
					resolve()
				}).catch(error => {
					reject(error)
				})
			})
		},
	},
}
export default dept