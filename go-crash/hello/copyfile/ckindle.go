package copyfile

import (
	"fmt"
	"io/fs"
	"io/ioutil"
	"log"
	"os"
	"path/filepath"
	"strings"
)

// src 和 dst 都是目录
func CopyAll(src, dst string) {
	names := listDir2(src, allFiles)
	log.Println(len(names))
	for _, name := range names {
		all := strings.Split(name, "\\")
		log.Println(all[len(all)-1])
		copyfile(name, dst+"\\"+all[len(all)-1])
	}
}

//拷贝文件src到文件dst
func copyfile(sourceFile, dstFile string) {
	input, err := ioutil.ReadFile(sourceFile)
	if err != nil {
		println(err)
		return
	}

	err = ioutil.WriteFile(dstFile, input, 0777)
	if err != nil {
		fmt.Println("创建文件错误:", dstFile)
		fmt.Println(err)
		return
	}
}

func listDir2(dir string, condition func(path string, info fs.FileInfo) bool) []string {
	var names []string
	err := filepath.Walk(dir, func(path string, info os.FileInfo, err error) error {
		if err != nil {
			return err
		}
		if condition(path, info) {
			names = append(names, path)
		}
		return nil
	})
	if err != nil {
		log.Fatalln(err)
	}
	return names
}

// kindle calibra 文件匹配规则
func kindleCondition(path string, info fs.FileInfo) bool {
	if !strings.Contains(path, ".jpg") &&
		!info.IsDir() &&
		!strings.Contains(path, ".opf") &&
		!strings.Contains(path, "metadata.db") &&
		!strings.Contains(path, "metadata_db") {
		return true
	}
	return false
}

//拷贝全文件
func allFiles(path string, info fs.FileInfo) bool {
	if !info.IsDir() {
		return true
	}
	return false
}
