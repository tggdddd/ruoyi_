import request from '@/utils/request'

// 查询部门（精简)列表
export const listSimpleDeptApi = async () => {
  return await request({ url: '/system/dept/list-all-simple' })
}

// 查询部门列表
export const getDeptPageApi = async (params) => {
  return await request({ url: '/system/dept/list', params })
}

// 查询部门详情
export const getDeptApi = async (id) => {
  return await request.get({ url: '/system/dept/get?id=' + id })
}

// 新增部门
export const createDeptApi = async (data) => {
  return await request.post({ url: '/system/dept/create', data: data })
}

// 修改部门
export const updateDeptApi = async (params) => {
  return await request.put({ url: '/system/dept/update', data: params })
}

// 删除部门
export const deleteDeptApi = async (id) => {
  return await request.delete({ url: '/system/dept/delete?id=' + id })
}
