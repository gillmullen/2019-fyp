declare i32 @printf(i8*, ...) #1
declare i32 @puts(i8*)
@.1arg_str = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1

define i32 @main ()
{
%.p.l = alloca i32
%.p.b = alloca i32
%.p.h = alloca i32
store i32 3, i32* %.p.l
store i32 4, i32* %.p.b
store i32 5, i32* %.p.h
%.t2 = load i32, i32* %.p.l
%.t3 = load i32, i32* %.p.b
%.t1 = mul i32 %.t2, %.t3
%.t5 = load i32, i32* %.p.b
%.t6 = load i32, i32* %.p.h
%.t4 = mul i32 %.t5, %.t6
call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, i32 0, i32 0), i32 %.t4)
ret i32 0
}
