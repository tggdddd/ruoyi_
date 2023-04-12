import store from '@/store'
const dept = (vue) => {
	vue.directive('dept', {
	  bind: function(el, binding) {
		  let label = "无"
		  const id = binding.value;
		if (store.getters && store.getters.dept) {
					  if(store.getters.dept.length == undefined || store.getters.dept.length == 0){
						  	store.dispatch("InitDept").then(()=>{
					  		let result = store.getters.dept.filter(item => item.id == id)
					  		if (result.length != 0) {
					  			label = result[0].name
					  		}
							el.innerHTML = label;
					  		return;
					  	})
					  }else{
						  let result = store.getters.dept.filter(item => item.id == id)
							if (result.length != 0) {
								label = result[0].name
							}
						  el.innerHTML = label;
						  return;
					  }
		}
		  el.innerHTML = label;
			
	  }
	});
}
export default dept