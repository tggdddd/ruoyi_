import request from '@/config/axios'

export interface FileVO {
  id: number
  configId: number
  path: string
  name: string
  url: string
  size: string
  type: string
  createTime: Date
}

export interface FilePageReqVO extends PageParam {
  path?: string
  type?: string
  createTime?: Date[]
}

// 查询文件列表
export const getFilePageApi = (params: FilePageReqVO) => {
  return request.get({ url: '/infra/file/page', params })
}

// 删除文件
export const deleteFileApi = (id: number) => {
  return request.delete({ url: '/infra/file/delete?id=' + id })
}

//表单的上传文件
export const uploadFileByFormApi = (param) => {
  return request.upload({
    url: '/infra/file/uploadByForm',
    param
  })
}
export const uploadFileByFormUrl =
  import.meta.env.VITE_BASE_URL + import.meta.env.VITE_API_URL + '/infra/file/uploadByForm'
