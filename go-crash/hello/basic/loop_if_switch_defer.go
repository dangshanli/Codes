package basic

import (
	"fmt"
	"math"
)

func Sum1() {
	sum := 0
	for i := 0; i < 10; i++ {
		sum += i
	}
	fmt.Println("sum=", sum)
}

func Sum2() {
	sum := 1
	for sum < 10000 {
		sum += sum
	}
	fmt.Println("sum2=", sum)
}

func NewtonSqrt(x float64) float64 {
	const delta = 0.00000000000001 //误差范围
	count := 0                     //计算次数计数器
	z := x / 2                     //才一个初始值
	for {                          //无限循环
		//据说叫牛顿法，不懂，但我深感佩服
		if math.Abs(math.Pow(z, 2)-x) > delta && count < 100 {
			z -= (z*z - x) / (2 * z)
			fmt.Println(count, "次,z=", z)
			count++
		} else {
			break
		}
	}
	return z
}
