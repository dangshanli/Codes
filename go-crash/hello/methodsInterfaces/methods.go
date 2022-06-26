package methodsinterfaces

import (
	"example/hello/myutil"
	"example/hello/other"
	"fmt"
	"math"
	"math/rand"
	"os"
	"strings"

	"io"

	"image"
	"image/color"

	"golang.org/x/tour/pic"
	"golang.org/x/tour/reader"
)

type Vertex struct {
	X, Y float64
}

//将函数挂载结构体值上，无法修改原结构体的值
func (v Vertex) Abs() float64 {
	return math.Sqrt(v.X*v.X + v.Y*v.Y)
}

//将方法挂载到指针结构体上，可以修改接受的内部变量
func (v *Vertex) Scale(scale float64) {
	v.X = v.X * scale
	v.Y = v.Y * scale
}

func Me() {
	fmt.Println("tell me")
	f := MyFloat(-3.456)
	fmt.Println(f.nbs())

	v := Vertex{3, 4}
	v.Scale(10)
	fmt.Println(v.Abs())

	useInterface()

	otherInterface()

	doReader()

	invokeRot()

	outPic()
}

type MyFloat float64

func (f MyFloat) nbs() float64 {
	myutil.PrintHeader("函数挂载到非结构体的type上")
	if f < 0 {
		return float64(-f)
	}
	return float64(f)
}

type Abser interface {
	Abs() float64
}

func useInterface() {
	myutil.PrintHeader("使用接口")
	var a Abser = Vertex{3, 4}
	fmt.Println(a.Abs())
}

type T struct {
	S string
}

func (t T) M() {
	fmt.Println("invoke method M by", t.S)
}

func (t T) B() int {
	rand.Seed(8964)
	return rand.Intn(778)
}

func otherInterface() {
	myutil.PrintHeader("接口定义在其他包中")
	var a other.I
	t := T{"rabbit"}
	a = t
	a.M()
	fmt.Println(a.B())
}

type Momo interface {
	M()
}

type MyReader struct{}

func (m MyReader) Read(bb []byte) (int, error) {
	for i := 0; i < len(bb); i++ {
		bb[i] = 'A'
	}
	return len(bb), nil
}

func doReader() {
	myutil.PrintHeader("小作业,实现Read方法")
	reader.Validate(MyReader{})
}

type rot13Reader struct {
	r io.Reader
}

func (rot rot13Reader) Read(bb []byte) (int, error) {
	x, err := rot.r.Read(bb)
	if err == nil {
		for i := 0; i < x; i++ {
			bb[i] = rot13(bb[i])
		}
	} else {
		fmt.Println(err)
	}
	return x, err
}

func rot13(b byte) byte {
	if b >= 'A' && b <= 'Z' {
		b = 'A' + (b-'A'+13)%26
	} else if b >= 'a' && b <= 'z' {
		b = 'a' + (b-'a'+13)%26
	}
	return b
}

func invokeRot() {
	myutil.PrintHeader("Read装饰器，rot13转换")
	s := strings.NewReader("Lbh penpxrq gur pbqr!")
	r := rot13Reader{s}
	io.Copy(os.Stdout, &r)
}

type Image struct {
	w, h int
}

func (m Image) Bounds() image.Rectangle {
	return image.Rect(0, 0, m.w, m.h)
}

func (m Image) ColorModel() color.Model {
	return color.RGBAModel
}

func (m Image) At(x, y int) color.Color {
	return color.RGBA{uint8(x * y), uint8(x * y), 255, 255}
}

func outPic() {
	myutil.PrintHeader("使用image package打印图片")
	m := Image{200, 150}
	pic.ShowImage(m)
}
