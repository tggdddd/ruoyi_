import type { App } from 'vue'
// 👇使用 form-create 需额外全局引入 element plus 组件
import {
  ElAside,
  ElPopconfirm,
  ElHeader,
  ElMain,
  ElContainer,
  ElDivider,
  ElTransfer,
  ElAlert,
  ElTabs,
  ElTable,
  ElTableColumn,
  ElTabPane,
  ElRow,
  ElCol
} from 'element-plus'
import UploadImg from '@/components/UploadFile/src/UploadImg.vue'
import UploadImgs from '@/components/UploadFile/src/UploadImgs.vue'
import UploadFile from '@/components/UploadFile/src/UploadFile.vue'
import formCreate from '@form-create/element-ui'
import install from '@form-create/element-ui/auto-import'
import FcDesigner from '@form-create/designer'

const components = [
  ElAside,
  ElPopconfirm,
  ElHeader,
  ElMain,
  ElContainer,
  ElDivider,
  ElTransfer,
  ElAlert,
  ElTabs,
  ElTable,
  ElTableColumn,
  ElTabPane,
  UploadImg,
  UploadImgs,
  UploadFile,
  ElRow,
  ElCol
]

export const setupFormCreate = (app: App<Element>) => {
  components.forEach((component) => {
    app.component(component.name, component)
  })

  formCreate.use(install)
  formCreate.component(UploadFile.name, UploadFile)
  formCreate.component(UploadImgs.name, UploadImgs)
  app.use(formCreate)

  app.use(FcDesigner)
}
