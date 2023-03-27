const useStatus = (vue) => {
	vue.directive('status', {
	  bind: function(el, binding) {
		  let label = "开启"
		  if(binding.value !== undefined){
			  label = binding.value == 1?"关闭":"开启"
		  }
		  el.innerHTML = label
	  }
	});
}
export default useStatus