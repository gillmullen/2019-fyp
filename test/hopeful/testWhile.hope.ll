declare i32 @printf(i8*, ...) #1
declare i32 @puts(i8*)
@.1arg_str = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1

define i32 @main ()
{
%.p.i = alloca i32
store i32 1, i32* %.p.i
br label %label1
label1: %.t1 = load i32, i32* %.p.i
%.t2 = icmp sle i32 %.t1, 5
br i1 %.t2, label %label2, label %label3
label2:
%.t4 = load i32, i32* %.p.i
%.t5 = load i32, i32* %.p.i
%.t3 = mul i32 %.t4, %.t5
call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, i32 0, i32 0), i32 %.t3)
%.t7 = load i32, i32* %.p.i
%.t6 = add i32 %.t7, 1
store i32 %.t6, i32* %.p.i
br label %label1
label3:
ret i32 0
}
