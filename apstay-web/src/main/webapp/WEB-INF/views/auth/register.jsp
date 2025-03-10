<%@ taglib prefix="register" tagdir="/WEB-INF/tags/auth" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.apu.apstay.enums.Gender" %>

<register:auth-template title="APStay - Registration">
    <div class="row justify-content-center">
        <div class="col-lg-6">
            <div class="p-5">
                <div class="text-center">
                    <h1 class="h4 text-gray-900 mb-4">Register an Account</h1>
                </div>
                <shared:notice-template />
                
                <form class="user" action="${pageContext.request.contextPath}/register" method="POST">

                    <div id="registrationAccordion">

                        <div class="card mb-3">
                            <div class="card-header" id="headingAccount">
                                <h5 class="mb-0">
                                    <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#collapseAccount" aria-expanded="true" aria-controls="collapseAccount">
                                        Account Information
                                    </button>
                                </h5>
                            </div>
                            <div id="collapseAccount" class="collapse show" aria-labelledby="headingAccount" data-parent="#registrationAccordion">
                                <div class="card-body">
                                    <div class="form-group">
                                        <input type="text" class="form-control form-control-user" id="username" name="username" placeholder="Username" required>
                                        <shared:validation-error field="username" />
                                    </div>
                                    <div class="form-group">
                                        <input type="email" class="form-control form-control-user" id="email" name="email"
                                               placeholder="Email Address" required>
                                        <shared:validation-error field="email" />
                                    </div>
                                    <!-- Password -->
                                    <div class="form-group">
                                        <input type="password" class="form-control form-control-user" id="password" name="password" 
                                               placeholder="Password" required>
                                        <shared:validation-error field="password" />
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="card mb-3">
                            <div class="card-header" id="headingPersonal">
                                <h5 class="mb-0">
                                    <button class="btn btn-link collapsed" type="button" data-toggle="collapse" data-target="#collapsePersonal" aria-expanded="false" aria-controls="collapsePersonal">
                                        Personal Details
                                    </button>
                                </h5>
                            </div>
                            <div id="collapsePersonal" class="collapse" aria-labelledby="headingPersonal" data-parent="#registrationAccordion">
                                <div class="card-body">
                                    <div class="form-group">
                                        <input type="text" class="form-control form-control-user" id="full-name" name="full-name" placeholder="Full Name" required>
                                        <shared:validation-error field="fullName" />
                                    </div>
                                    <div class="form-group">
                                        <input type="text" class="form-control form-control-user" id="identity-number" name="identity-number" placeholder="Identity Number" required>
                                        <shared:validation-error field="identityNumber" />
                                    </div>
                                    <div class="form-group">
                                        <input type="tel" class="form-control form-control-user" id="phone" name="phone" pattern="[0-9]{10}" placeholder="0123456789" required>
                                        <shared:validation-error field="phone" />
                                    </div>
                                    <div class="form-group">
                                        <select class="form-control" id="gender" name="gender" required>
                                            <option value="">Select Gender</option>
                                            <c:forEach items="<%= Gender.values() %>" var="genderOption">
                                                <option value="${genderOption.name()}">${genderOption.toString()}</option>
                                            </c:forEach>
                                        </select>
                                        <shared:validation-error field="gender" />
                                    </div>
                                    <div class="form-group">
                                        <textarea class="form-control" id="address" name="address" placeholder="Address" rows="3"></textarea>
                                        <shared:validation-error field="address" />
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="card mb-3">
                            <div class="card-header" id="headingUnit">
                                <h5 class="mb-0">
                                    <button class="btn btn-link collapsed" type="button" data-toggle="collapse" data-target="#collapseUnit" aria-expanded="false" aria-controls="collapseUnit">
                                        Hostel Unit
                                    </button>
                                </h5>
                            </div>
                            <div id="collapseUnit" class="collapse" aria-labelledby="headingUnit" data-parent="#registrationAccordion">
                                <div class="card-body">
                                    <div class="form-group">
                                        <select class="form-control" id="unit-id" name="unit-id" required>
                                            <option value="">Select a Unit</option>
                                            <c:forEach items="${availableUnits}" var="unit">
                                                <option value="${unit.id()}">${unit.unitName()}-${unit.floorNumber()}</option>
                                            </c:forEach>
                                        </select>
                                        <shared:validation-error field="unitId" />
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>

                    <button type="submit" class="btn btn-primary btn-user btn-block">
                        Register
                    </button>
                </form>
                <hr>
                <div class="text-center">
                    <a class="small" href="${pageContext.request.contextPath}/login">
                        Already have an account? Login!
                    </a>
                </div>
            </div>
        </div>
    </div>
</register:auth-template>
