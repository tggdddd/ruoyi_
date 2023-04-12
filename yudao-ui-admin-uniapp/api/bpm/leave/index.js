import request from '@/utils/request'


// 创建请假申请
export const createLeaveApi = async (data) => {
  return await request({ url: '/bpm/oa/leave/create', data: data ,
	method:'post'})
}

// 获得请假申请
export const getLeaveApi = async (id) => {
  return await request({ url: '/bpm/oa/leave/get?id=' + id })
}

// 获得请假申请分页
export const getLeavePageApi = async (params) => {
  return await request({ url: '/bpm/oa/leave/page', params })
}
