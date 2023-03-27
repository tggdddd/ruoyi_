import store from '@/store'
const useDirect = (vue) => {
	vue.directive('dictData', {
	  bind: function(el, binding) {
		  let label = "无"
		  const type = binding.value.type;
		  const value = binding.value.value;
		  if (store.getters && store.getters.dict) {
			  if(store.getters.dict[type] == undefined){
			  	store.dispatch("Init").then(()=>{
			  		let result = store.getters.dict[type].filter(item => item.value == value)
			  		if (result.length != 0) {
			  			label = result[0].label
			  		}
					el.innerHTML = label;
			  		return;
			  	})
			  }else{
				  let result = store.getters.dict[type].filter(item => item.value == value)
				  if (result.length != 0) {
				  	label = result[0].label
				  }
				  el.innerHTML = label;
			  }
		  }
	  }
	});
}
export default useDirect