import request from '@/config/axios'

export interface ontractTemplateVO {
  id: number
  name: string
  postId: number
  identityCard: string
  salary: number
  attach: string
  performanceRequirements: string
  defaultClause: string
  firstParty: string
  signedTime: Date
  startTime: Date
  endTime: Date
}

export interface ontractTemplatePageReqVO extends PageParam {
  name?: string
  postId?: number
  identityCard?: string
  salary?: number
  performanceRequirements?: string
  defaultClause?: string
  firstParty?: string
  signedTime?: Date[]
  startTime?: Date[]
  endTime?: Date[]
  createTime?: Date[]
}

export interface ontractTemplateExcelReqVO {
  name?: string
  postId?: number
  identityCard?: string
  salary?: number
  performanceRequirements?: string
  defaultClause?: string
  firstParty?: string
  signedTime?: Date[]
  startTime?: Date[]
  endTime?: Date[]
  createTime?: Date[]
}

// 查询合同表单模板列表
export const getontractTemplatePageApi = async (params: ontractTemplatePageReqVO) => {
  return await request.get({ url: '/c/ontract-template/page', params })
}

// 查询合同表单模板详情
export const getontractTemplateApi = async (id: number) => {
  return await request.get({ url: '/c/ontract-template/get?id=' + id })
}

// 新增合同表单模板
export const createontractTemplateApi = async (data: ontractTemplateVO) => {
  return await request.post({ url: '/c/ontract-template/create', data })
}

// 修改合同表单模板
export const updateontractTemplateApi = async (data: ontractTemplateVO) => {
  return await request.put({ url: '/c/ontract-template/update', data })
}

// 删除合同表单模板
export const deleteontractTemplateApi = async (id: number) => {
  return await request.delete({ url: '/c/ontract-template/delete?id=' + id })
}

// 导出合同表单模板 Excel
export const exportontractTemplateApi = async (params: ontractTemplateExcelReqVO) => {
  return await request.download({ url: '/c/ontract-template/export-excel', params })
}

//根据ID获得合同页面
export const getAttachByIdApi = async (id: number) => {
  return await request.get({ url: '/c/ontract-template/getAttach?id=' + id })
}
