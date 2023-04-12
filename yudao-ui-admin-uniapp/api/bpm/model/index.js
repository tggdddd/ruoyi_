import request from '@/utils/request'

export const getModelPageApi = async (params) => {
  return await request({ url: '/bpm/model/page', params })
}

export const getModelApi = async (id) => {
  return await request({ url: '/bpm/model/get?id=' + id })
}

export const updateModelApi = async (data: ModelVO) => {
  return await request({ url: '/bpm/model/update', data: data,
	method:'put' })
}

// 任务状态修改
export const updateModelStateApi = async (id, state) => {
  const data = {
    id: id,
    state: state
  }
  return await request({ url: '/bpm/model/update-state', data: data,
	method:'put' })
}

export const createModelApi = async (data) => {
  return await request({ url: '/bpm/model/create', data: data,
	method:'post' })
}

export const deleteModelApi = async (id) => {
  return await request({ url: '/bpm/model/delete?id=' + id,
	method:'delete'  })
}

export const deployModelApi = async (id) => {
  return await request({ url: '/bpm/model/deploy?id=' + id ,
	method:'post' })
}
