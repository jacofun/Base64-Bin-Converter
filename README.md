# Base64-Bin-Converter
This is a Base64 / bin file converter with java GUI


## 2023-11-27
将new FileInputStream(inputFileName)修改为Files.newInputStream(Paths.get(inputFileName))
性能优势：在某些情况下，基于NIO的解决方案（如 Files.newInputStream）可能比传统的I/O解决方案（如 FileInputStream）在性能上有优势，尤其是在处理大量数据或需要高效率I/O操作时。
