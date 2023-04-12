import {
	getProcessDefinitionName
} from '@/api/bpm/definition/index.js'

const processDefiend = (vue) => {
	vue.directive('processDefiend', {
	  bind: function(el, binding) {
		  let label = "无"
		  const id = binding.value;
		  getProcessDefinitionName(id).then(res => {
			  label = res.data
		  }).finally(()=>{
			    el.innerHTML = label;
		  })
		}
	});
}
export default processDefiend