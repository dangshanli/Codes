package moretype

import "fmt"

func Pp() {
	i, j := 42, 2701

	p := &i // &取地址符号
	fmt.Println(*p) //*放在类型前是声明指针类型，放在变量前取值
	*p = 21; 
	fmt.Println(i)//i通过指针被赋值21

	p = &j
	*p = *p/37
	fmt.Println(j) // 2701/37
}
