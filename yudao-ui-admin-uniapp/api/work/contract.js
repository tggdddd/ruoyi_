import request from '@/utils/request'

// 查询合同表单列表
export const getontractPageApi = async (params) => {
  return await request({ url: '/c/ontract/page', params })
}

// 查询合同表单详情
export const getontractApi = async (id) => {
  return await request({ url: '/c/ontract/get?id=' + id })
}

// 新增合同表单
export const createontractApi = async (data) => {
  return await request({ url: '/c/ontract/create', data, method:'post' })
}

// 修改合同表单
export const updateontractApi = async (data) => {
  return await request({ url: '/c/ontract/update', data, method:'put' })
}
// 查询生效的合同表单列表
export const getAcceptContractPageApi = async (params) => {
  params.status = 1
  return await request({ url: '/c/ontract/page', params })
}
// 删除合同表单
export const deleteontractApi = async (id) => {
  return await request({ url: '/c/ontract/delete?id=' + id , method:'delete'})
}

// 导出合同表单 Excel
export const exportontractApi = async (params) => {
  return await request.download({ url: '/c/ontract/export-excel', params })
}

//根据ID获得合同页面
export const getAttachByIdApi = async (id) => {
  return await request({ url: '/c/ontract/getAttach?id=' + id })
}
