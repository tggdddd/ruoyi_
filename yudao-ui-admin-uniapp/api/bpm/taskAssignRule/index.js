import request from '@/utils/request'



export const getTaskAssignRuleList = async (params) => {
  return await request({ url: '/bpm/task-assign-rule/list', params })
}

export const createTaskAssignRule = async (data) => {
  return await request({
    url: '/bpm/task-assign-rule/create',
    data: data,
	method:'post'
  })
}

export const updateTaskAssignRule = async (data) => {
  return await request({
    url: '/bpm/task-assign-rule/update',
    data: data,
	method:'put'
  })
}
