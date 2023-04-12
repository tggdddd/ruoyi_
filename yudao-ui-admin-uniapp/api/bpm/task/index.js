import request from '@/utils/request'

export const getTodoTaskPage = async (params) => {
  return await request({ url: '/bpm/task/todo-page', params })
}

export const getDoneTaskPage = async (params) => {
  return await request({ url: '/bpm/task/done-page', params })
}

export const completeTask = async (data) => {
  return await request({ url: '/bpm/task/complete', data ,
	method:'put'})
}

export const approveTask = async (data) => {
  return await request({ url: '/bpm/task/approve', data,
	method:'put' })
}

export const rejectTask = async (data) => {
  return await request({ url: '/bpm/task/reject', data ,
	method:'put'})
}
export const backTask = async (data) => {
  return await request({ url: '/bpm/task/back', data,
	method:'put' })
}

export const updateTaskAssignee = async (data) => {
  return await request({ url: '/bpm/task/update-assignee', data,
	method:'put' })
}

export const getTaskListByProcessInstanceId = async (processInstanceId) => {
  return await request({
    url: '/bpm/task/list-by-process-instance-id?processInstanceId=' + processInstanceId
  })
}
