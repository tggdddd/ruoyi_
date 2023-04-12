import request from '@/utils/request'

export const getProcessDefinitionBpmnXMLApi = async (id) => {
  return await request({
    url: '/bpm/process-definition/get-bpmn-xml?id=' + id
  })
}

export const getProcessDefinitionPageApi = async (params) => {
  return await request({
    url: '/bpm/process-definition/page',
    params
  })
}

export const getProcessDefinitionListApi = async (params) => {
  return await request({
    url: '/bpm/process-definition/list',
    params
  })
}

export const getProcessDefinitionListOnlyPerformApi = async (params) => {
  params.category = 2
  return await request({
    url: '/bpm/process-definition/list',
    params
  })
}

export const getProcessDefinitionApi = async (key) => {
  return await request({
    url: '/bpm/process-definition/get',
    params: { id: key }
  })
}
export const getProcessDefinitionName = async (id) => {
	return await request({
		url: '/bpm/process-definition/getName2',
		params:{id : id}
	})
}