import request from '@/utils/request'



// 创建工作流的表单定义
export const createFormApi = async (data) => {
  return await request({
    url: '/bpm/form/create',
    data: data,
	method:'post'
  })
}

// 更新工作流的表单定义
export const updateFormApi = async (data) => {
  return await request({
    url: '/bpm/form/update',
    data: data,
	method:'put'
  })
}

// 删除工作流的表单定义
export const deleteFormApi = async (id) => {
  return await request({
    url: '/bpm/form/delete?id=' + id,
	method:'delete'
  })
}

// 获得工作流的表单定义
export const getFormApi = async (id) => {
  return await request({
    url: '/bpm/form/get?id=' + id
  })
}

// 获得工作流的表单定义分页
export const getFormPageApi = async (params) => {
  return await request({
    url: '/bpm/form/page',
    params
  })
}

// 获得动态表单的精简列表
export const getSimpleFormsApi = async () => {
  return await request({
    url: '/bpm/form/list-all-simple'
  })
}
