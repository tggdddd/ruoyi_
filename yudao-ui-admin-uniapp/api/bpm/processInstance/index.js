import request from '@/utils/request'


export const getMyProcessInstancePageApi = async (params) => {
  return await request({ url: '/bpm/process-instance/my-page', params })
}

export const createProcessInstanceApi = async (data) => {
  return await request({ url: '/bpm/process-instance/create', data: data,
	method:'post'  })
}

export const cancelProcessInstanceApi = async (id, reason) => {
  const data = {
    id: id,
    reason: reason
  }
  return await request({ url: '/bpm/process-instance/cancel', data: data,
	method:'delete'  })
}

export const getProcessInstanceApi = async (id) => {
  return await request({ url: '/bpm/process-instance/get?id=' + id })
}
