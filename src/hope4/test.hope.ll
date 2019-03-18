declare i32 @printf(i8*, ...) #1
declare i32 @puts(i8*)
@.1arg_str = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1

define i32 @main ()
{
%.p.a = alloca i32
%.p.b = alloca i32

store i32 1, i32* %.p.a

store i32 2, i32* %.p.b
%.t1 = load i32, i32* %.p.b
%.t2 = load i32, i32* %.p.a
%.t3 = icmp sle i32 %.t1, %.t2
br i1 %.t3, label %label1, label %label2
label1:
%.t4 = load i32, i32* %.p.b

call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, i32 0, i32 0), i32 %.t4)
br label %label3
label2:
%.t5 = load i32, i32* %.p.a

call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, i32 0, i32 0), i32 %.t5)
br label %label3
label3:
ret i32 0
}
