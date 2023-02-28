import request from '@/config/axios'

export interface ontractVO {
  id: number
  userId: number
  postId: number
  name: string
  identityCard: string
  salary: number
  attach: string
  performanceRequirements: string
  defaultClause: string
  status: number
  firstParty: string
  signedTime: Date
  startTime: Date
  endTime: Date
}

export interface ontractPageReqVO extends PageParam {
  userId?: number
  name?: string
  postId: number
  identityCard?: string
  salary?: number
  attach?: string
  performanceRequirements?: string
  defaultClause?: string
  status?: number
  firstParty?: string
  signedTime?: Date[]
  startTime?: Date[]
  endTime?: Date[]
  createTime?: Date[]
}

export interface ontractExcelReqVO {
  userId?: number
  name?: string
  postId: number
  identityCard?: string
  salary?: number
  attach?: string
  performanceRequirements?: string
  defaultClause?: string
  status?: number
  firstParty?: string
  signedTime?: Date[]
  startTime?: Date[]
  endTime?: Date[]
  createTime?: Date[]
}

// 查询合同表单列表
export const getontractPageApi = async (params: ontractPageReqVO) => {
  return await request.get({ url: '/c/ontract/page', params })
}

// 查询合同表单详情
export const getontractApi = async (id: number) => {
  return await request.get({ url: '/c/ontract/get?id=' + id })
}

// 新增合同表单
export const createontractApi = async (data: ontractVO) => {
  return await request.post({ url: '/c/ontract/create', data })
}

// 修改合同表单
export const updateontractApi = async (data: ontractVO) => {
  return await request.put({ url: '/c/ontract/update', data })
}

// 删除合同表单
export const deleteontractApi = async (id: number) => {
  return await request.delete({ url: '/c/ontract/delete?id=' + id })
}

// 导出合同表单 Excel
export const exportontractApi = async (params: ontractExcelReqVO) => {
  return await request.download({ url: '/c/ontract/export-excel', params })
}

//根据ID获得合同页面
export const getAttachByIdApi = async (id: number) => {
  return await request.get({ url: '/c/ontract/getAttach?id=' + id })
}
